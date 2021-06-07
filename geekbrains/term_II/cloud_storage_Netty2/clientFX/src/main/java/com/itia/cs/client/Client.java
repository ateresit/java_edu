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
        Parent root = FXMLLoader.load(getClass().getResource("/general.fxml"));

        InputStream iconStream = getClass().getResourceAsStream("/cloud_storage_iTIA.jpg");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.setTitle("Cloud Storage v1 -- iTIA");
        primaryStage.setScene(new Scene(root,1220,700));
        primaryStage.setMinWidth(1220);
        primaryStage.setMinHeight(700);
        primaryStage.setMaxWidth(1500);
        primaryStage.setMaxHeight(1000);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
