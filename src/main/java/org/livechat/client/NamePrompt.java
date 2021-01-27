package org.livechat.client;

import javafx.scene.control.TextInputDialog;

public class NamePrompt {

    private String userName = "You";

    NamePrompt(){
        TextInputDialog tD = new TextInputDialog();
        tD.setGraphic(null);
        tD.setHeaderText("Enter your name:");
        tD.showAndWait();
        userName = tD.getEditor().getText();
    }

    public String getName() {
        return userName;
    }

}
