package gui.controllers;

import core.Authorization;
import core.Item;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import gui.GuiMain;
import gui.Main;
import gui.SalesView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SalesMainController {

    // Sales Pane Controllers
    @FXML private ComboBox<String> cboxSalesViewStyle;                //Style : view Style
    @FXML private DatePicker dpSalesDatePicker;                       //Date : view sales by date
    @FXML private TableView<SalesView> tableSales;                    //Table : Sales
    @FXML private TableColumn<SalesView, Integer> cnSalesId;          //Column : ID
    @FXML private TableColumn<SalesView, String> cnSalesName;         //Column : Name
    @FXML private TableColumn<SalesView, Integer> cnSalesAmount;      //Column : Amount
    @FXML private TableColumn<SalesView, Double> cnSalesValue;        //Column : PurchaseValue
    @FXML private TableColumn<SalesView, Double> cnSalesPrice;        //Column : Sold Price
    @FXML private TableColumn<SalesView, Double> cnSalesPaid;         //Column : Paid
    @FXML private TableColumn<SalesView, DateFormat> cnSalesDate;     //Column : Date
    @FXML private Label lblTotalSales;
    @FXML private TextField txtSelectedItem;
    @FXML private TextField txtSelectedAmount;
    @FXML private TextField txtSelectedValue;
    @FXML private TextField txtSelectedPrice;
    @FXML private TextField txtSelectedPaid;
    @FXML private TextField txtSalesSearch;
    @FXML private Button btnSelectedDelete;

    private TpsWindowController parent;
    private ObservableList<SalesView> salesList;        //current sales list

    public void init() {
        parent = GuiMain.getTpsWindowController();

        //init DatePicker
        dpSalesDatePicker.setValue(LocalDate.now());
        cboxSalesViewStyle.setItems(parent.getDataViewStyleList());
        cboxSalesViewStyle.setValue(parent.getDataViewStyleList().get(1));

        //setup authorizations for sales controls
        txtSelectedItem.setEditable(Authorization.EDIT_SALES_ITEM);
        txtSelectedAmount.setEditable(Authorization.EDIT_SALES_AMOUNT);
        txtSelectedValue.setEditable(Authorization.EDIT_SALES_VALUE);
        txtSelectedPrice.setEditable(Authorization.EDIT_SALES_PRICE);
        txtSelectedPaid.setEditable(Authorization.EDIT_SALES_PAID);
        btnSelectedDelete.setDisable(!Authorization.DELETE_SALES);

        // init Sales Table
        cnSalesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnSalesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cnSalesAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        cnSalesValue.setCellValueFactory(new PropertyValueFactory<>("purchaseValue"));
        cnSalesPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cnSalesPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        cnSalesDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        salesList = FXCollections.observableArrayList();

        tableSales.setItems(salesList);

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<SalesView> filteredSalesList = new FilteredList<>(salesList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txtSalesSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredSalesList.setPredicate(sales -> {
                // If filter text is empty, display all Sales.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare Item Name with filter text.
                String strFilter = newValue;

                if(sales.getName().contains(strFilter)){
                    return true; // Filter matches this item.
                }

                return false;    // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<SalesView> sortedSalesList = new SortedList<>(filteredSalesList);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedSalesList.comparatorProperty().bind(tableSales.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableSales.setItems(sortedSalesList);

        /**
         * bind txtTotalSales which display Total Sales
         * of the table Sales
         * that means whenever the table updates the TextField
         * will update too
         */
        DoubleBinding total = Bindings.createDoubleBinding(() ->
                        tableSales.getItems().stream().collect(Collectors.summingDouble(SalesView::getPaid)),
                tableSales.getItems()
        );

        lblTotalSales.textProperty().bind(total.asString("%3.0f"));

    }

    @FXML //view Sales depends on the chosen date and style
    public void btnSalesViewOnAction(){

        parent.showLoading();
        salesList.clear();

        new Thread(
                new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try{

                            // get outgoings
                            List<Item> sales =
                                    GuiMain.getSalesControl().getItems(dpSalesDatePicker.getValue(),
                                            parent.getDataViewStyle(cboxSalesViewStyle));

                            /**
                             * update observable list
                             * that will update Items
                             * Table and total label too
                             */
                            Platform.runLater(() -> {

                                salesList.addAll(sales.stream().map(item -> new SalesView(
                                        item.getId(),
                                        item.getName(),
                                        item.getAmount(),
                                        item.getPrice(),
                                        item.getPaid(),
                                        item.getPurchaseValue(),
                                        GuiMain.getSalesControl().getItemDate(item).toString()
                                )).collect(Collectors.toList()));
                            });

                        }catch (WSConnException ex){
                            //TODO: add no connection GUI alert
                            System.out.println("no connection");
                        }catch(NoDataException ex){
                            //TODO: add no Data GUI alert
                            System.out.println("no data received");
                        } finally {
                            parent.hideLoading(); // hide loading pane
                        }
                        return null;
                    }
                }
        ).start();
    }

    @FXML
    public void tableSalesOnMouseClicked(){
        if(tableSales.getSelectionModel().isEmpty())
            return;

        SalesView item = tableSales.getSelectionModel().getSelectedItem();
        txtSelectedItem.setText(item.getName());
        txtSelectedAmount.setText(String.valueOf(item.getAmount()));
        txtSelectedValue.setText(String.valueOf(item.getPurchaseValue()));
        txtSelectedPrice.setText(String.valueOf(item.getPrice()));
        txtSelectedPaid.setText(String.valueOf(item.getPaid()));
    }
}
