package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(GuiMain.class.getResource("Login.fxml"));
        primaryStage.centerOnScreen();
        primaryStage.setTitle("إدارة المبيعات - تسجيل الدخول");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        GuiMain.setLoginWindow(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
