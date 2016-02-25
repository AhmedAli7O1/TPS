package gui;

import core.UpdateControl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private UpdateControl updateControl;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GuiMain.setAppSettings(new AppSettings("tps.properties"));

        // load settings file if not found just create a new one and close the app.
        if(GuiMain.getAppSettings().loadSettings()){
            System.out.println("settings loaded");
        }
        else {
            System.out.println("couldn't load settings file");
            System.exit(0);
        }

        updateControl = new UpdateControl();

        new Thread(
                new Task() {
                    @Override
                    protected Object call() throws Exception {
                        Thread.sleep(GuiMain.getAppSettings().getSplashTimeout());
                        boolean result = updateControl.checkForUpdates();
                        Platform.runLater(() -> updateCallBack(result));
                        return null;
                    }
                }
        ).start();
    }

    private void updateCallBack(boolean result){
        if(result){
            // open TPS App
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-jar", GuiMain.getAppSettings().getAppPath());
                pb.start();
            }catch (Exception ex){ ex.printStackTrace(); }
        }
        //close splash screen
        GuiMain.getMainStage().close();
    }
}
