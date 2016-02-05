package gui;

import core.Item;
import core.SalesControl;
import core.SalesViewStyle;
import core.igui.ISalesControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController extends Main implements Initializable {

    @FXML private VBox salesVBox;
    @FXML private VBox outgoingsVBox;
    @FXML private VBox purchasesVBox;
    @FXML private Label lblUserName;

    // Sales Pane Controllers
    @FXML private ComboBox<String> cboxSalesViewStyle;
    @FXML private DatePicker dpSalesDatePicker;
    @FXML private Pane paneLoading;
    @FXML private TableView<SalesView> tableSales;
    @FXML private TableColumn<SalesView, Integer> cnSalesId;
    @FXML private TableColumn<SalesView, String> cnSalesName;
    @FXML private TableColumn<SalesView, Integer> cnSalesAmount;
    @FXML private TableColumn<SalesView, Double> cnSalesSoldPrice;
    @FXML private TableColumn<SalesView, Double> cnSalesPaid;
    @FXML private TableColumn<SalesView, DateFormat> cnSalesDate;
    private ObservableList<SalesView> salesList;

    private ObservableList<String> salesViewStyle;

    private List<VBox> panes;

    private ISalesControl salesControl;

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

        salesControl = new SalesControl();

        // initialize Sales controllers
        initSalesControllers();
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
    @FXML
    private void btnAddSalesOnClick(){
        SubWindow subWin = new SubWindow("sales sub window", "layout/AddSales.fxml");
        subWin.show();
    }

    @FXML
    private void btnSalesViewOnAction(){

        paneLoading.setVisible(true);
        salesVBox.setDisable(true);
        salesList.clear();

        new Thread(
                () -> {
                    try {

                        List<Item> items = salesControl.getItems(dpSalesDatePicker.getValue(), getSalesViewStyle());

                        for (Item item : items) {

                            salesList.add(new SalesView(
                                    item.getId(),
                                    item.getName(),
                                    item.getAmount(),
                                    item.getSoldPrice(),
                                    item.getPaid(),
                                    item.getDate().toString()));
                        }
                    }
                    catch(Exception ex){
                    }
                    finally {
                        salesVBox.setDisable(false);
                        paneLoading.setVisible(false);
                    }
                }
        ).start();
    }

    /**
     * ############################################
     * ######### Initialize of controls ###########
     * ############################################
     */

    // initialize Sales controllers
    private void initSalesControllers(){
        //init DatePicker
        dpSalesDatePicker.setValue(LocalDate.now());

        // init View Style ComboBox
        salesViewStyle = FXCollections.observableArrayList();
        salesViewStyle.add("يومى");
        salesViewStyle.add("شهرى");
        salesViewStyle.add("سنوى");

        cboxSalesViewStyle.setItems(salesViewStyle);

        cboxSalesViewStyle.setValue(salesViewStyle.get(1));

        // init Sales Table
        cnSalesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnSalesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cnSalesAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        cnSalesSoldPrice.setCellValueFactory(new PropertyValueFactory<>("soldPrice"));
        cnSalesPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        cnSalesDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        salesList = FXCollections.observableArrayList();

        tableSales.setItems(salesList);
    }

    private SalesViewStyle getSalesViewStyle(){
        if(cboxSalesViewStyle.getSelectionModel().isSelected(1))
            return SalesViewStyle.MONTH;
        else if(cboxSalesViewStyle.getSelectionModel().isSelected(0))
            return SalesViewStyle.DAY;
        else if(cboxSalesViewStyle.getSelectionModel().isSelected(2))
            return SalesViewStyle.YEAR;
        else
            return null;
    }
}
