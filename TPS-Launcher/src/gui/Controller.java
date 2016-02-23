package gui;

import core.UpdateControl;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    UpdateControl updateControl;
    Properties props = new Properties();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            props.load(getClass().getResourceAsStream("tps.properties"));
        }catch (Exception ex){ ex.printStackTrace(); }

        updateControl = new UpdateControl(Integer.parseInt(props.getProperty("ver")));


    }
}
