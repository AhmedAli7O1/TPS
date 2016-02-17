package gui.controllers;

import core.exceptions.WSConnException;
import gui.GuiMain;
import gui.Main;
import gui.windows.TpsWindow;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LoginController extends Main implements Initializable {

    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private ImageView imageState;
    @FXML private Pane paneLoading;
    @FXML private VBox paneMain;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //empty for now
        imageState.setImage(new Image(GuiMain.class.getResourceAsStream("login_64x64.png")));
        imageState.resize(64,64);

    }

    @FXML // fire this event when user clicks Login Button
    public void btnLoginOnAction(){

        paneLoading.setVisible(true);
        paneMain.setDisable(true);

        new Thread(
                new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try{
                            boolean result = GuiMain.getUserControl().login(txtUserName.getText(), txtPassword.getText());

                            Platform.runLater(() ->{
                                if(result){
                                    GuiMain.getLoginWindow().close();     // close login window
                                    GuiMain.getTpsWindow().show();        // open main window
                                    GuiMain.getStatisticsMainController().btnViewOnAction();
                                }
                                else {
                                    //error image
                                    imageState.setImage(new Image(GuiMain.class.getResourceAsStream("error_64x64.png")));
                                    paneMain.setDisable(false);
                                    paneLoading.setVisible(false);
                                }
                            });
                        }
                        catch (WSConnException ex){
                            // TODO : show GUI connection error alert
                            System.out.print("connection error");
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                        finally {
                            Platform.runLater(()->{
                                paneMain.setDisable(false);
                                paneLoading.setVisible(false);
                            });
                        }
                        return null;
                    }
                }
        ).start();
    }
}
