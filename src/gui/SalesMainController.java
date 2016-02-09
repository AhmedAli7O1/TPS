package gui;

import core.Item;
import core.Outgoing;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
                new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try{
                            // get outgoings
                            List<Item> sales =
                                    parent.getSalesControl().getItems(dpSalesDatePicker.getValue(),
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
                                        item.getSoldPrice(),
                                        item.getPaid(),
                                        item.getDate().toString()
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

        txtTotalSales.textProperty().bind(Bindings.format("%3.2f", total));
    }

}
