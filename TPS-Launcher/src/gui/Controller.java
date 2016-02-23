package gui;

import core.DownloadUpdate;
import core.UpdateControl;
import core.exceptions.WSConnException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private Label lblState;

    private UpdateControl updateControl;
    private Properties props = new Properties();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get app path to save any downloaded updates into
        String jarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "TPS.jar";
        try {
            props.load(getClass().getResourceAsStream("tps.properties"));
        }catch (Exception ex){ ex.printStackTrace(); }

        updateControl = new UpdateControl(jarPath, Integer.parseInt(props.getProperty("ver")));

        new Thread(
                new Task() {
                    @Override
                    protected Object call() throws Exception {
                        boolean result = updateControl.checkForUpdates();
                        Platform.runLater(() -> updateCallBack(result));
                        return null;
                    }
                }
        ).start();

    }

    private void updateCallBack(boolean result){
        if(result)
            System.out.println("true");
        else System.out.println("false");
    }
}
