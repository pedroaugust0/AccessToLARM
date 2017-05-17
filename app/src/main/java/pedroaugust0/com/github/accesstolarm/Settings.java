package pedroaugust0.com.github.accesstolarm;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by par on 12/05/17.
 * Classe responsável por configurar Ip, Porta, Credencial e salvar no arquivo
 */


class Settings {

    //DEBUG and INFO
    private static final String LOG_TAG = "Settings.";

    private String pathDir;
    private String settingsFile;

    public Settings(Context context){

        File appDir = context.getExternalFilesDir(null);

        if(appDir == null || (!appDir.exists() && !appDir.mkdirs())){
            appDir = context.getFilesDir();
            Log.i(LOG_TAG, "appDir NOT OK, try again");

        } else{
            appDir = context.getFilesDir();
            Log.i(LOG_TAG, "appDir OK");
        }

        settingsFile = context.getResources().getString(R.string.settings_name) + ".txt";
        Log.i(LOG_TAG, "settingsFile: " + settingsFile);
        pathDir = appDir.getAbsolutePath() + context.getResources().getString(R.string.settings_folder_name);
        Log.i(LOG_TAG, "pathDir: " + pathDir);
    }


    public void setSettings(String settings){

        File file;
        FileOutputStream save;

        try {
            file = new File(pathDir, settingsFile);
            file.getParentFile().mkdirs();
            save = new FileOutputStream(file);
            save.write(settings.getBytes());
            save.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String[] searchSettings(){

        File file;
        String splitSettings[] = null;

        try {
            file = new File(pathDir, settingsFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            splitSettings = bufferedReader.readLine().split("#");
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return splitSettings;
    }

    public boolean hasSettingFile(){

        File file = new File(pathDir);
        File[] appFiles = file.listFiles();

        if (appFiles != null){
            return true;
        } else {
            return false;
        }

    }


}
