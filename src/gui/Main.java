package gui;

import core.OutgoingsControl;
import core.SalesControl;
import core.igui.IOutgoingsControl;
import core.igui.ISalesControl;
import core.igui.IUserControl;
import core.UserControl;
import gui.sales.AddSalesWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    protected static AddSalesWindow addSalesWindow;
    protected static IUserControl userControl = new UserControl();
    protected static ISalesControl salesControl = new SalesControl();
    protected static IOutgoingsControl outgoingsControl = new OutgoingsControl();

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.centerOnScreen();
        primaryStage.setTitle("إدارة المبيعات - تسجيل الدخول");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    protected void showMainScene(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
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
