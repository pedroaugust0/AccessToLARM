package pedroaugust0.com.github.accesstolarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "Main.";



    /*INICIO TRECHO PARA PERMISSÃO DE GRAVAR ARQUIVO NO EXTERNAL STORAGE E LER
    * Se nao tiver permissão o aplicativo eh fechado*/
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        Log.d(LOG_TAG, "onCreate");

        Settings settings = new Settings(this);

        if(!settings.hasSettingFile()){
            Intent intent = new Intent(this,FillData.class);
            this.startActivity(intent);
        }
    }




    public void onOpen(View view) {

        try{
            ConnectToServer connectToServer = new ConnectToServer(this);
            connectToServer.openTheDoor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, R.string.open_menssage, Toast.LENGTH_SHORT).show();
    }


    public void onSettings (View view){
        Intent intent = new Intent(this, FillData.class);
        this.startActivity(intent);

    }
}
