package com.itia.cs.client;

import com.itia.cs.client.handlers.ServerMessageHandler;
import com.itia.cs.commands.Command;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    VBox clientPanel;

    @FXML
    VBox serverPanel;

    @FXML
    Button connectBtn;

    @FXML
    Button disconnectBtn;

    @FXML
    HBox commandButtons;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    private Network network;
    private boolean authenticated;
    private ServerPanelController serverPanelController;
    private ServerMessageHandler serverMessageHandler;
    public static List<FileInfo> fileInfoList;

    public void setAuthenticated(boolean authenticated){
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

//    public void sendMsgAction(ActionEvent actionEvent) {
//        network.sendMessage(commandLine.getText());
//        commandLine.clear();
//        commandLine.requestFocus();
    }


    public void btnExitAction(ActionEvent actionEvent) {
        network.closeConnection();
        setAuthenticated(false);
        Platform.exit();
    }

    public void uploadBtnAction(ActionEvent actionEvent) {
        ClientPanelController clientPanelController = (ClientPanelController) clientPanel.getProperties().get("control");
        ServerPanelController serverPanelController = (ServerPanelController) serverPanel.getProperties().get("control");

        if (clientPanelController.getSelectedFileName() == null && serverPanelController.getSelectedFileName() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не выбрано содержимое для действия.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

    }

    public void connect () {
        network = new Network();

    }

    public void connectBtnAction(ActionEvent actionEvent) {
//        network = new Network();
        if (network == null) {
            connect();
        }
//        network.sendMessage(String.format("%s %s %s", Command.AUTHY, loginField.getText().trim(), passwordField.getText().trim()));
//        setAuthenticated(serverMessageHandler.isAuthenticated());
//        System.out.println(serverMessageHandler.isAuthenticated());
    }

    public void disconnectBtnAction(ActionEvent actionEvent) {
        if (network != null) {
            network.closeConnection();
        }

        setAuthenticated(false);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
