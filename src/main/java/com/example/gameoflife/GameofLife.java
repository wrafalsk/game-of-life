package com.example.gameoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameofLife extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene scene = new Scene(loader.load(), 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
