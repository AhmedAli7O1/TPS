package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddSalesWindow extends Stage{

    public AddSalesWindow(){
        try{
            initialize();
        }
        catch (Exception ex){

        }
    }

    private void initialize() throws Exception{
        setTitle("برنامج إدارة المبيعات - إضافة فاتورة");
        Parent root = FXMLLoader.load(getClass().getResource("AddSales.fxml"));
        Scene scene = new Scene(root);
        setScene(scene);
        sizeToScene();
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
    }
}
