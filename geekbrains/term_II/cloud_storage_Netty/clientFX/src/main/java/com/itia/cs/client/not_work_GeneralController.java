package com.itia.cs.client;

import com.itia.cs.commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.*;
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
    private boolean authenticated;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button connectBtn;

    @FXML
    Button disconnectBtn;

    @FXML
    HBox commandButtons;


    /**
     * Переключение видимости панелей на панели клиента если авторизация удачная
     * @param authenticated
     */
    public void switchPanelVisibility (boolean authenticated) {
        this.authenticated = authenticated;

        disconnectBtn.setVisible(authenticated);
        disconnectBtn.setManaged(authenticated);

        commandButtons.setVisible(authenticated);
        commandButtons.setManaged(authenticated);

        connectBtn.setVisible(!authenticated);
        connectBtn.setManaged(!authenticated);

        loginField.setVisible(!authenticated);
        loginField.setManaged(!authenticated);
        passwordField.setVisible(!authenticated);
        passwordField.setManaged(!authenticated);
    }


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



            new Thread(() -> {
                try {
                    /** Цикл аутентификации */
                    while (true) {
 //                       String serverMSG = in.readUTF();
                        byte srvMSG = in.readByte();
                        System.out.println(serverMSG);



                        if (serverMSG.startsWith("/")) {
                            if (serverMSG.equals(Command.AUTHY_ERROR)) {
                                switchPanelVisibility(false);
                                Alert loginError = new Alert(Alert.AlertType.ERROR, "Ошибка авторизации!", ButtonType.OK);
                                loginError.showAndWait();
                                return;
                            } else {
                                switchPanelVisibility(true);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }).start();






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
