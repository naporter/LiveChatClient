package org.livechat.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WriteThread extends Thread{

    private final Socket socket;
    private DataOutputStream dos;
    private final Controller clientController;


    WriteThread(Socket socket, Controller clientController){
        this.socket = socket;
        this.clientController = clientController;
        try{
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            System.out.println("Could not establish connection.");
        }
    }

    public void run(){
        try {
            String string = "working";
            while(!dos.equals(".exit")){
                dos.writeUTF(string);
                System.out.println(string);
            }
            closeSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeSocket() throws IOException{
        socket.close();
    }
}
