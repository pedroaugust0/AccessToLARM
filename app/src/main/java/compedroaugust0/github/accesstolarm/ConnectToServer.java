package compedroaugust0.github.accesstolarm;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by par on 12/05/17.
 */

class ConnectToServer {

    private static final String LOG_TAG = "ConnectToServer.";
    private Socket socket;
    private DataOutputStream msgOut;


    public ConnectToServer(Context context) throws Exception{

        Settings settings = new Settings(context);

        String[] splitSettings = settings.searchSettings();


//        String ip = "150.162.234.151";
//        int porta = 9879;
//        socket = new Socket(ip, porta);

        String ip = splitSettings[0];
        int porta = Integer.parseInt(splitSettings[1]);
        Log.i(LOG_TAG, "IP: " + ip + " porta: " + porta );

    }

//    class ThreadSocket implements Runnable{
//        String ip = "150.462.234.151";
//        int porta = 9999;
//
//        @Override
//        public void run(){
//
//            try{
//                socket = new Socket(ip, porta);
//
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.i(LOG_TAG, "Conexão Estabelecida");
//
//        }
//
//    }

    public void openTheDoor(){

        criaData();
        Log.i(LOG_TAG, "Criou DataOutputStream");

        try {
            msgOut.writeUTF("OK");
            msgOut.flush();
            Log.i(LOG_TAG, "MSG Enviada");
            msgOut.close();
            Log.i(LOG_TAG, "DataOutputStream Fechado");


        } catch (IOException e) {
            e.printStackTrace();
        }


        //        PrintWriter out = null;
//        try {
//            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        out.println("OK");
//        Log.i(LOG_TAG, "MSG Enviada");
//        out.close();

    }


    public void criaData(){

        try {
            msgOut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
