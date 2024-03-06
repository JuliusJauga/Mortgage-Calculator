package com.example.paskolos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Julius Jauga 5 gr.
 * Main class of application. Starts the application.
 */
public class MortgageApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MortgageApplication.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 720);
        stage.setTitle("Mortgage Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}