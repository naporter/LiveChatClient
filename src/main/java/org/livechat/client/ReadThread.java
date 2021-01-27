package org.livechat.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThread extends Thread {
    private DataInputStream dis;
    private final Socket socket;
    private final Controller clientController;

    ReadThread(Socket socket, Controller clientController){
        this.socket = socket;
        this.clientController = clientController;
        this.setDaemon(true);
        try{
            dis = new DataInputStream(socket.getInputStream());
        }catch (IOException e){
            System.out.println("Could not make connection");
        }

    }

    public void run() {
        try{
            while (!socket.isClosed()) {
                String fromWho = dis.readUTF();
                if(fromWho.equals("sendingUsers")){
                    int numUsers = dis.read();
                    for (int i = 0; i < numUsers; i++){
                        clientController.appendUsers(dis.readUTF());
                    }
                } else {
                    String incomingText = dis.readUTF();
                    clientController.appendConvoText("From " + fromWho + ": " + incomingText);
                }
            }
            closeSocket();
        }catch(IOException e){
            System.out.println("Connection lost.");
            clientController.appendConvoText("Connection to server lost.");
        }

    }
    public void closeSocket() throws IOException {
        socket.close();
    }
}
