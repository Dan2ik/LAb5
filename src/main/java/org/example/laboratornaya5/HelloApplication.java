
package org.example.laboratornaya5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javax.swing.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load(); // Загружаем root объект через loader
        // Создаем сцену и задаем параметры окна
        primaryStage.setTitle("Habitat Simulation");
        primaryStage.setScene(new Scene(root, 700, 600));  // размеры окна 700x600
        primaryStage.show();  // отображаем окно
    }


    public static void main(String[] args) {
        launch(args);  // Запуск JavaFX-приложения
    }
}