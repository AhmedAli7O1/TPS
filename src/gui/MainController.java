package gui;

import core.DataViewStyle;
import core.igui.IOutgoingsControl;
import core.igui.ISalesControl;
import gui.outgoings.OutgoingsMainController;
import gui.purchases.PurchasesMainController;
import gui.sales.AddSalesWindow;
import gui.sales.SalesMainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController extends Main implements Initializable, IParentController {

    @FXML private VBox purchasesMain;
    @FXML private PurchasesMainController purchasesMainController;
    @FXML private VBox outgoingsMain;
    @FXML private OutgoingsMainController outgoingsMainController;
    @FXML private VBox salesMain;                             //Sales Window
    @FXML private SalesMainController salesMainController;    //Sales Window Controller
    @FXML private Label lblUserName;                          //label shows current user name
    @FXML private Pane paneLoading;                           //Loading Pane
    private List<VBox> panes;                                 //List of panes "Sales, Outgoings, Withdrawals...etc"
    private VBox currPane;                                    //Current Pane
    private ObservableList<String> dataViewStyleList;      //how sales are displayed daily, monthly...etc


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // create list contains all panes to control them
        panes = new ArrayList<>();
        panes.add(salesMain);
        panes.add(outgoingsMain);
        panes.add(purchasesMain);

        // by default all panes not visible
        salesMain.setVisible(false);
        outgoingsMain.setVisible(false);
        purchasesMain.setVisible(false);

        // init View Style ComboBox
        dataViewStyleList = FXCollections.observableArrayList();
        dataViewStyleList.add("يومى");
        dataViewStyleList.add("شهرى");
        dataViewStyleList.add("سنوى");

        // get user data
        lblUserName.setText(userControl.getCurrentUser().getName());

        //initialize all sub Windows with this instance as parent
        salesMainController.setMainController(this);       //Sales
        outgoingsMainController.setMainController(this);   //Outgoings
    }

    //sales TitledPane onMouseClicked Event
    public void salesOnClicked(){ showPane(salesMain); }
    //outgoings TitledPane onMouseClicked Event
    public void outgoingsOnClicked() { showPane(outgoingsMain); }
    //purchases TitledPane onMouseClicked Event
    public void purchasesOnClicked() { showPane(purchasesMain); }

    @Override // show and hide panes "Windows"
    public void showPane(VBox pane){
        panes.forEach(p -> {
            if(p == pane) {
                p.setVisible(true);
                currPane = pane;
            }
            else
                p.setVisible(false);
        });
    }

    @Override
    public void showLoading(){
        paneLoading.setVisible(true);
        currPane.setDisable(true);
    }

    @Override
    public void hideLoading(){
        currPane.setDisable(false);
        paneLoading.setVisible(false);
    }


    /**
     * OnAction Event for button AddSales
     * when user click this button
     * a new window will show to add new order
     */
    @FXML
    private void btnAddSalesOnClick(){
        addSalesWindow = new AddSalesWindow();
        addSalesWindow.showAndWait();
        try {
            salesControl.addNewOrders();
        }catch (Exception ex){}
        salesMainController.btnSalesViewOnAction();
    }

    @Override //read ComboBox value and return it as SalesViewStyle
    public DataViewStyle getDataViewStyle(ComboBox<String> cbox){
        if(cbox.getSelectionModel().isSelected(1))
            return DataViewStyle.MONTH;
        else if(cbox.getSelectionModel().isSelected(0))
            return DataViewStyle.DAY;
        else if(cbox.getSelectionModel().isSelected(2))
            return DataViewStyle.YEAR;
        else
            return null;
    }

    @Override
    public ObservableList<String> getDataViewStyleList() {
        return dataViewStyleList;
    }

    @Override
    public ISalesControl getSalesControl(){
        return salesControl;
    }

    @Override
    public IOutgoingsControl getOutgoingsControl(){
        return outgoingsControl;
    }
}
