package gui;

import core.igui.ISalesControl;
import core.igui.IUserControl;
import core.UserControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    protected static IUserControl userControl = new UserControl();
    protected static ISalesControl salesControl;
    private static Stage primaryStage;
    protected static AddSalesWindow addSalesWindow;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("layout/Login.fxml"));
        primaryStage.centerOnScreen();
        primaryStage.setTitle("إدارة المبيعات - تسجيل الدخول");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    protected void showMainScene(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/Main.fxml"));
            primaryStage.centerOnScreen();
            primaryStage.setTitle("برنامج إدارة المبيعات");
            primaryStage.setScene(new Scene(root));
            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            primaryStage.show();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
