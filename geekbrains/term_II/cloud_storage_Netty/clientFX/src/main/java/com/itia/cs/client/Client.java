package com.itia.cs.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client.fxml"));

        InputStream iconStream = getClass().getResourceAsStream("/cloud_storage_iTIA.jpg");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.setTitle("Cloud Storage v1");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.setMaxWidth(900);
        primaryStage.setMaxHeight(900);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
