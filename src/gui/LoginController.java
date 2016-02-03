package gui;

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
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        boolean result = userControl.login(userName, password);
        if (result) {
            System.out.println("true");
            showMainScene();
        }
        else
            System.out.println("false");
    }
}
