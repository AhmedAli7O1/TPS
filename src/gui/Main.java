package gui;

import core.*;
import core.igui.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    protected static AddSalesWindow addSalesWindow;
    protected static AddOutgoingsWindow addOutgoingsWindow;
    protected static IUserControl userControl = new UserControl();
    protected static ISalesControl salesControl = new SalesControl();
    protected static IOutgoingsControl outgoingsControl = new OutgoingsControl();
    protected static IIncomesControl incomesControl = new IncomesControl();
    protected static IWithdrawalsControl withdrawalsControl = new WithdrawalsControl();
    protected static IPurchasesControl purchasesControl = new PurchasesControl();

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
