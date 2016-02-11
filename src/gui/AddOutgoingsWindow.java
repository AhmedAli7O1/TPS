package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Ahmed ali on 11/02/2016.
 */
public class AddOutgoingsWindow extends Stage {

    public AddOutgoingsWindow(){
        try{
            initialize();
        }
        catch (Exception ex){

        }
    }

    private void initialize() throws Exception{
        setTitle("برنامج إدارة المبيعات - إضافة مصروفات");
        Parent root = FXMLLoader.load(getClass().getResource("AddOutgoings.fxml"));
        Scene scene = new Scene(root);
        setScene(scene);
        sizeToScene();
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
    }
}
