package gui.windows;

import gui.GuiMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Ahmed ali on 21/02/2016.
 */
public class AddPurchasesWindow extends Stage{
    public AddPurchasesWindow(){
        try{
            initialize();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initialize() throws Exception {
        setTitle("برنامج إدارة المبيعات - إضافة مشتريات");
        Parent root = FXMLLoader.load(GuiMain.class.getResource("AddPurchases.fxml"));
        Scene scene = new Scene(root);
        setScene(scene);
        sizeToScene();
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
    }
}
