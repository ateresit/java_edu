package com.itia.cs.client;

import com.itia.cs.commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class GeneralController implements Initializable {
    private Socket socket;
    private final int SERVER_PORT = 7799;
    private final String SERVER_IP = "localhost";
    private DataInputStream in;
    private DataOutputStream out;

    @FXML
    public TextField loginField;

    @FXML
    public PasswordField passwordField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Метод передачи данных для авторизации, срабатывает при нажатии кнопки "Подключиться"
     *
     */
    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()){
            connectServer();
        }

        try {
            out.writeUTF(String.format("%s %s %s", Command.AUTHY, loginField.getText().trim(), passwordField.getText().trim()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            passwordField.clear();
        }
    }

    /**
     * Метод подключения к серверу
     *
     */
    private void connectServer(){
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

 //           new Thread(() -> {

                /** Цикл аутентификации */
 /*               while (true){

                }

            }).start();

  */


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void closeConnection(ActionEvent actionEvent) {
    }

    public void tryToUpload(ActionEvent actionEvent) {
    }

    public void exitAction(ActionEvent actionEvent) {
    }
}
