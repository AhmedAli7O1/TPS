package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController extends Main implements Initializable {

    @FXML private VBox salesVBox;
    @FXML private VBox outgoingsVBox;
    @FXML private VBox purchasesVBox;

    private List<VBox> panes;

    @FXML private Label lblUserName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // create list contains all panes to control them
        panes = new ArrayList<>();
        panes.add(salesVBox);
        panes.add(outgoingsVBox);
        panes.add(purchasesVBox);

        // by default all panes not visible
        salesVBox.setVisible(false);
        outgoingsVBox.setVisible(false);
        purchasesVBox.setVisible(false);

        // get user data
        lblUserName.setText(userControl.getCurrentUser().getName());
    }

    /**
     * ###################################################
     * ############# Event handling Methods ##############
     * ###################################################
     */

    //sales TitledPane onMouseClicked Event
    public void salesOnClicked(){ showPane(salesVBox); }
    //outgoings TitledPane onMouseClicked Event
    public void outgoingsOnClicked() { showPane(outgoingsVBox); }
    //purchases TitledPane onMouseClicked Event
    public void purchasesOnClicked() { showPane(purchasesVBox); }

    // show and hide panes "Windows"
    private void showPane(VBox pane){
        panes.forEach(p -> {
            if(p != pane)
                p.setVisible(false);

            pane.setVisible(true);
        });
    }

    /**
     * OnAction Event for button AddSales
     * when user click this button
     * a new window will show to add new order
     */
    public void btnAddSalesOnClick(){
        SubWindow subWin = new SubWindow("sales sub window", "AddSalesWindow.fxml");
        subWin.show();
    }
}
