package pedroaugust0.com.github.accesstolarm;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Classe/Activity Responsável por preencher os dados para a conexão via Socket
 */

public class FillData extends Activity {

    //DEBUG and INFO
    private static final String LOG_TAG = "FillData.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Quando a activity esta sendo iniciada pela primeira vez
        Log.d(LOG_TAG, "onCreate");

        setContentView(R.layout.activity_fill_data);
        //Inicia o layout da activity

        Settings settings = new Settings(this);

        if(settings.hasSettingFile()) {

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

    @Override
    public void onDestroy(){
        Log.i(LOG_TAG, "Activity Closed");
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        super.onDestroy();
    }


    public void onSave(View view){

        String newSettings;

        EditText newIp = (EditText) findViewById(R.id.ip_edit_text);
        EditText newPort = (EditText) findViewById(R.id.port_edit_text);
        EditText newCredential = (EditText) findViewById(R.id.credential_edit_text);

        newSettings = newIp.getText().toString() + "#" + newPort.getText().toString()
                + "#" + newCredential.getText().toString() + "#";

        Log.i(LOG_TAG, "newSettings: " + newSettings);

        Settings settings = new Settings(this);
        settings.setSettings(newSettings);

        Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();


        onDestroy();
    }


}
