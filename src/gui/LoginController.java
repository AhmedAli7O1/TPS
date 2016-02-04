package gui;

import core.Authorization;
import core.exceptions.LoginException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class LoginController extends Main implements Initializable {

    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //empty for now
    }

    @FXML
    private void btnLoginOnAction(){
        try{
            boolean result = userControl.login(txtUserName.getText(), txtPassword.getText());
            if(result){
                System.out.println("logged");
            }
        }
        catch (LoginException ex){
            if(ex.getExType() == LoginException.ExType.INVALID_ID_PASS){
                // open alert
                // temp msg
                System.out.println("invalid id & pass");
            }
            else
                ex.printStackTrace();
        }
    }
}
