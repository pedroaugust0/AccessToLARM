package pedroaugust0.com.github.accesstolarm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
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
    ProgressDialog progressDialog;
    TabHost abas;
    private boolean hasSettings = true;
//  Requisitar permissão do usuário.


    private static final int REQUEST_PERMISSIONS = 300;
    private boolean permissionsOfApp = false;
    private String [] permissions = {Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE};

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


        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        Log.d(LOG_TAG, "onCreate");

        Settings settings = new Settings(this);

        if (!settings.hasSettingFile()) {
            hasSettings = false;
            abas.setCurrentTab(1);
            Log.d(LOG_TAG, "Entrou Aqui");

        } else {
           updateDataSettings();
        }

    }

    public void onOpen(View view) {

        if(!hasSettings){
            Toast.makeText(this, R.string.insert_data, Toast.LENGTH_SHORT).show();
            updateDataSettings();
            abas.setCurrentTab(1);

        } else {

            String title = getResources().getString(R.string.loading_title);
            String message = getResources().getString(R.string.loading_message);


            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(title);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);



            if(isConnected()){
                Thread buttonsActionsThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connectionRun();
                    }
                },"ButtonsActionsThread");

                progressDialog.show();
                progressDialog.setMessage(message);

                buttonsActionsThread.start();

            } else {
                Toast.makeText(this, R.string.no_wifi_menssage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public  boolean isConnected() {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        connected = connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();
        return connected;
    }



    void connectionRun(){

        try{
             ConnectToServer connectToServer = new ConnectToServer(this);
             connectToServer.openTheDoor();
        } catch (Exception e) {
             e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();
    }



    public void onSave(View view) {
        String newSettings = "198.162.200.181#1234#A34DCV9Y#";

        EditText newIp = (EditText) findViewById(R.id.ip_edit_text);
        EditText newPort = (EditText) findViewById(R.id.port_edit_text);
        EditText newCredential = (EditText) findViewById(R.id.credential_edit_text);

        if (newIp.getText().length() != 0) {
            newSettings = newIp.getText().toString() + "#" + newPort.getText().toString()
                    + "#" + newCredential.getText().toString().toUpperCase() + "#";
        }


        Log.i(LOG_TAG, "newSettings: " + newSettings);

        Settings settings = new Settings(this);
        settings.setSettings(newSettings);

        Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();

        hasSettings = true;
        abas.setCurrentTab(0);
    }


    void updateDataSettings(){
        Settings settings = new Settings(this);

        String[] splitSetting = settings.searchSettings();

        EditText ipText, portText, credentialText;
        ipText = (EditText) findViewById(R.id.ip_edit_text);
        portText = (EditText) findViewById(R.id.port_edit_text);
        credentialText = (EditText) findViewById(R.id.credential_edit_text);

        ipText.setText(splitSetting[0]);
        portText.setText(splitSetting[1]);
        credentialText.setText(splitSetting[2]);

    }
}

