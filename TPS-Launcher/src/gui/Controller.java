package gui;

import core.UpdateControl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private UpdateControl updateControl;
    private String jarPath;
    private String settingsPath;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
                // get app path to save any downloaded updates into
                //appPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
                jarPath = "TPS.jar";
                settingsPath = "tps.properties";

                GuiMain.setAppSettings(new AppSettings(settingsPath));
                updateControl = new UpdateControl(jarPath, GuiMain.getAppSettings().getVerNum());

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
                // set version number
                GuiMain.getAppSettings().setVerNum(updateControl.getLastUpdateVer());
                GuiMain.getAppSettings().save();

            // open TPS App
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-jar", jarPath);
                pb.start();
            }catch (Exception ex){ ex.printStackTrace(); }
        }
        //close splash screen
        GuiMain.getMainStage().close();
    }
}
