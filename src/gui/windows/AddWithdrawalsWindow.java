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
public class AddWithdrawalsWindow extends Stage{
    public AddWithdrawalsWindow(){
        try{
            initialize();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initialize() throws Exception {
        setTitle("برنامج إدارة المبيعات - إضافة مسحوبات");
        Parent root = FXMLLoader.load(GuiMain.class.getResource("AddWithdrawals.fxml"));
        Scene scene = new Scene(root);
        setScene(scene);
        sizeToScene();
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
    }
}
