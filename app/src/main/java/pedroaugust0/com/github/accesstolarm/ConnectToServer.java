package pedroaugust0.com.github.accesstolarm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Classe responsável por conectar com o Servidor via Socket, e mandar mensagem com
 *  a credencial para abrir a porta.
 */

class ConnectToServer {

    private static final String LOG_TAG = "ConnectToServer.";
    private Socket socket;
    private String credential;

    ConnectToServer(Context context){

        Settings settings = new Settings(context);

        String[] splitSettings = settings.searchSettings();

        String ip = splitSettings[0];
        int porta = Integer.parseInt(splitSettings[1]);
        credential = splitSettings[2];
        Log.i(LOG_TAG, "IP: " + ip + " porta: " + porta );
        try {
            socket = new Socket(ip, porta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void openTheDoor(){
        PrintWriter msgOut;
        try {
            msgOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            Log.i(LOG_TAG, "Criou PrintWriter");
            msgOut.print(credential);
            msgOut.flush();
            msgOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

