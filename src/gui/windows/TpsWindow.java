package gui.windows;

import gui.GuiMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Ahmed ali on 14/02/2016.
 */
public class TpsWindow extends Stage {

    public TpsWindow(){
        try{
            initialize();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initialize()throws Exception {
        setTitle("برنامج إدارة المبيعات - الرئيسية");
        Parent root = FXMLLoader.load(GuiMain.class.getResource("TpsWindow.fxml"));
        Scene scene = new Scene(root);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(true);
        setMaximized(true);
    }
}
