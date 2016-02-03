package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SubWindow extends Stage{

    public SubWindow(String title, String fxmlFile){
        super(StageStyle.DECORATED);
        try {
            initialize(title, fxmlFile);
        }catch(Exception ex){}
    }

    private void initialize(String title, String fxmlFile) throws Exception{
        setTitle(title);
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        setScene(scene);
        sizeToScene();
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
    }
}
