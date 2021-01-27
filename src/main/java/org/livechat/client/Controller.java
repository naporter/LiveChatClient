package org.livechat.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private final ArrayList<String> conClients = new ArrayList<>();
    private int numConnections;
    private String myId;
    private String myName;
    private NamePrompt np;

    public TextArea convoBox;
    public TextField messageBox;
    public ComboBox activeUsers;

    @FXML
    public void sendMsg() {
        if(activeUsers.getSelectionModel().getSelectedItem() == null){
            appendConvoText("You must select a user to send your message to.");
        }else{
            IOException disconnect = new IOException();
            try {
                if(messageBox.getText().equals(".exit")){
                    messageBox.clear();
                    messageBox.setDisable(true);
                    throw disconnect;
                }
                String sendingTo = activeUsers.getSelectionModel().getSelectedItem().toString();
                dos.writeUTF(sendingTo);
                dos.writeUTF(messageBox.getText());
                appendConvoText("To " + sendingTo + ": " + messageBox.getText());
                messageBox.clear();
            } catch (IOException e) {
                appendConvoText("No connection to the server.");
            }
        }
    }

    private void startConnection() {
        System.out.println("Connection started");
        try {
            socket = new Socket("127.0.0.1", 3000);
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(myName); //send users name;
//            dis = new DataInputStream(socket.getInputStream());
//            numConnections = dis.read();
//            for(int i = 0; i <= numConnections; i++){
//                conClients.add(dis.readUTF());
//            }
            new ReadThread(socket, this).start();
        } catch (IOException e) {
            appendConvoText("Server is inactive. Try restarting client application and ensure Server is started.");
            messageBox.setDisable(true);
        }
    }

    public void appendConvoText(String s){
        convoBox.appendText(s + "\n");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        np = new NamePrompt();
        this.myName = np.getName();
        startConnection();
    }

    public void getActiveClients() throws IOException {
        dos.writeUTF("getActiveClients");

    }
    public void appendUsers(String user){
        if(!activeUsers.getItems().contains(user)) {
            activeUsers.getItems().add(user);
        }
    }
}
