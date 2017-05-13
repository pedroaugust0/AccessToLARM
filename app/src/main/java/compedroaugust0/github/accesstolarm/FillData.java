package compedroaugust0.github.accesstolarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by par on 12/05/17.
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
