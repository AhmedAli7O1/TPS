package gui.sales;

import core.Item;
import gui.IParentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;

public class SalesMainController {

    // Sales Pane Controllers
    @FXML private ComboBox<String> cboxSalesViewStyle;                //Style : view Style
    @FXML private DatePicker dpSalesDatePicker;                       //Date : view sales by date
    @FXML private TableView<SalesView> tableSales;                    //Table : Sales
    @FXML private TableColumn<SalesView, Integer> cnSalesId;          //Column : ID
    @FXML private TableColumn<SalesView, String> cnSalesName;         //Column : Name
    @FXML private TableColumn<SalesView, Integer> cnSalesAmount;      //Column : Amount
    @FXML private TableColumn<SalesView, Double> cnSalesSoldPrice;    //Column : Sold Price
    @FXML private TableColumn<SalesView, Double> cnSalesPaid;         //Column : Paid
    @FXML private TableColumn<SalesView, DateFormat> cnSalesDate;     //Column : Date
    @FXML private TextField txtTotalSales;

    private IParentController parent;                   //represent all methods needed from MainController
    private ObservableList<SalesView> salesList;        //current sales list

    //MainController calls this method to set the parent interface
    public void setMainController(IParentController parent){
        this.parent = parent;
        initSalesControllers();
    }

    @FXML //view Sales depends on the chosen date and style
    public void btnSalesViewOnAction(){

        parent.showLoading();
        salesList.clear();

        new Thread(
                () -> {
                    Double totalPaid = 0d;
                    try {
                        List<Item> items =
                                parent.getSalesControl().getItems(dpSalesDatePicker.getValue(),
                                        parent.getDataViewStyle(cboxSalesViewStyle));
                        for (Item item : items) {
                            totalPaid += item.getPaid();
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
                        txtTotalSales.setText(totalPaid.toString());
                        parent.hideLoading();
                    }
                }
        ).start();
    }

    // initialize Sales controllers
    private void initSalesControllers(){
        //init DatePicker
        dpSalesDatePicker.setValue(LocalDate.now());
        cboxSalesViewStyle.setItems(parent.getDataViewStyleList());
        cboxSalesViewStyle.setValue(parent.getDataViewStyleList().get(1));

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

}
