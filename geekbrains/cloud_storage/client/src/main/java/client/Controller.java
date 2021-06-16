package client;

import commands.Command;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

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
      network = new Network();
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

    public void connectServer () {
        network = new Network();

    }

    public void connectBtnAction(ActionEvent actionEvent) {
        if (network == null) {
            connectServer();
        }
        network.sendMessage(String.format("%s %s %s", Command.AUTHY, loginField.getText().trim(), passwordField.getText().trim()));

    }

    public void disconnectBtnAction(ActionEvent actionEvent) {

    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
