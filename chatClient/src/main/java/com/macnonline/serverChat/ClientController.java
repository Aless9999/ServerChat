package com.macnonline.serverChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    TextArea mainArea;
    @FXML
    TextField message;
    private Network network;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        network=new Network((args -> mainArea.appendText((String)args[0])));

    }

    public void sendMsgMessage(ActionEvent actionEvent) {
        System.out.println(message.getText());
        network.sendMessage(message.getText());
        message.clear();
        message.requestFocus();


    }




}