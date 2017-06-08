package pedroaugust0.com.github.accesstolarm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Main.";

    TabHost abas;
    private boolean hasSettings = true;
    private int count = 0;

//  Requisitar permissão do usuário.

    private WifiManager wifiManager;

    private static final int REQUEST_PERMISSIONS = 300;
    private boolean permissionsOfApp = false;
    private String[] permissions = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE};

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                permissionsOfApp = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }

        if (!permissionsOfApp){
            Log.d(LOG_TAG, "Permissão Recusadas");
            finish();
        } else{
            Log.d(LOG_TAG, "Permissão Concedidas");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        abas = (TabHost) findViewById(R.id.tabHost);
        abas.setup();

        TabHost.TabSpec descritor = abas.newTabSpec("aba1");
        descritor.setContent(R.id.tab1);
        descritor.setIndicator(getString(R.string.open));
        abas.addTab(descritor);

        descritor = abas.newTabSpec("aba2");
        descritor.setContent(R.id.tab2);
        descritor.setIndicator(getString(R.string.settings_name));
        abas.addTab(descritor);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


//        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        Log.d(LOG_TAG, "onCreate");

        Settings settings = new Settings(this);

        if (!settings.hasSettingFile()) {
            hasSettings = false;
            abas.setCurrentTab(1);
        } else {
            updateDataSettings();
        }


    }

    public void onOpen(View view) {

        int cont = 0;

        if(!hasSettings){
            Toast.makeText(this, R.string.insert_data, Toast.LENGTH_SHORT).show();
            updateDataSettings();
            abas.setCurrentTab(1);

        } else {

            // Verificando se WiFi está ligado
            isWifiOn(this);

            // Verificando se está no WIFI do LARM
            if (connectedOnLarm()){
                Thread buttonsActionsThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connectionRun();
                    }
                },"ButtonsActionsThread");

                buttonsActionsThread.start();
                Toast.makeText(this, R.string.open_menssage, Toast.LENGTH_SHORT).show();
                count = 0;
                finish();

            } else {
                if (count < 3) {
                    Toast.makeText(this, R.string.connectionOnLarm, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, R.string.try_again, Toast.LENGTH_SHORT).show();
                    count++;
                } else {
                    Toast.makeText(this, R.string.erro_wifi_password, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void isWifiOn(Context context) {

        wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            while (!wifiManager.isWifiEnabled());
            wifiManager.startScan();
        }
    }


    public boolean connectedOnLarm(){

        WifiInfo connections;
        connections = wifiManager.getConnectionInfo();
        Log.d(LOG_TAG, "SSID: " + connections.getSSID());

        if(connections.getSSID().endsWith("\"LARM\"")){
            Log.d(LOG_TAG, "on LARM" );
        } else {
            Toast.makeText(this, R.string.connectionOnLarm, Toast.LENGTH_SHORT).show();

            String[] splitStrings = new Settings(this).searchSettings();


            Log.d(LOG_TAG, "not LARM" );
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\""+"LARM"+"\"";
            wifiConfiguration.preSharedKey = "\""+splitStrings[3]+"\"";
            Log.d(LOG_TAG, "splitString[3]: " +splitStrings[3] );

            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

            int wifiId = wifiManager.addNetwork(wifiConfiguration);
            wifiManager.enableNetwork(wifiId, true);
            Log.d(LOG_TAG, "fim das config" );
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()){


            connections = wifiManager.getConnectionInfo();
            if(connections.getSSID().endsWith("\"LARM\"")) {
                return true;
            }
        }

//        wifiManager.reconnect();
        return false;
    }


    void connectionRun(){

        try{
            ConnectToServer connectToServer = new ConnectToServer(this);
            connectToServer.openTheDoor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onSave(View view) {
        String newSettings = "198.162.200.181#1234#A34DCV9Y#KnowNothing!";

        EditText newIp = (EditText) findViewById(R.id.ip_edit_text);
        EditText newPort = (EditText) findViewById(R.id.port_edit_text);
        EditText newCredential = (EditText) findViewById(R.id.credential_edit_text);
        EditText newPassword = (EditText) findViewById(R.id.wifi_edit_text);

        if (newIp.getText().length() != 0) {
            newSettings = newIp.getText().toString() + "#" + newPort.getText().toString()
                    + "#" + newCredential.getText().toString().toUpperCase() + "#"
                    + newPassword.getText().toString() + "#";
        }

        Log.d(LOG_TAG, "newSettings: " + newSettings);
        Settings settings = new Settings(this);
        settings.setSettings(newSettings);

        Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();

        hasSettings = true;
        abas.setCurrentTab(0);

    }


    void updateDataSettings(){
        Settings settings = new Settings(this);

        String[] splitSetting = settings.searchSettings();

        EditText ipText, portText, credentialText, wifiText;
        ipText = (EditText) findViewById(R.id.ip_edit_text);
        portText = (EditText) findViewById(R.id.port_edit_text);
        credentialText = (EditText) findViewById(R.id.credential_edit_text);
        wifiText = (EditText) findViewById(R.id.wifi_edit_text);
        ipText.setText(splitSetting[0]);
        portText.setText(splitSetting[1]);
        credentialText.setText(splitSetting[2]);
        wifiText.setText(splitSetting[3]);
    }
}