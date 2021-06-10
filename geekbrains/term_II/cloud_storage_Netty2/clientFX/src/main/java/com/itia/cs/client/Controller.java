package com.itia.cs.client;

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

    public void connectBtnAction(ActionEvent actionEvent) {
        System.out.println("в поле ЛОГИН ввели " + loginField.getText());
        network = new Network();
 //       network.sendMessage(loginField.getText());
        setAuthenticated(true);
    }

    public void disconnectBtnAction(ActionEvent actionEvent) {
        network.closeConnection();
        setAuthenticated(false);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
