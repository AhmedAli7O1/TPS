package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSalesController implements Initializable{
    @FXML private TextField txtItemToAdd;
    @FXML private TextField txtCountToAdd;
    @FXML private TextField txtPriceToAdd;
    @FXML private TextField txtCodeToAdd;
    @FXML private TableView<OrderView> tableOrders;
    @FXML private TextField txtRealPrice;
    @FXML private TextField txtDiscount;
    @FXML private TextField txtTotal;
    @FXML private TextField txtDiscountPercentage;

    @FXML private TableColumn<OrderView, String> itemColumn;
    @FXML private TableColumn<OrderView, String> amountColumn;
    @FXML private TableColumn<OrderView, String> priceColumn;
    @FXML private TableColumn<OrderView, String> codeColumn;

    private ObservableList<OrderView> orders;

    private double realPrice;
    private double discount;
    private double totalPrice;
    private double discountPercentage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init local variables
        realPrice = 0;
        discount = 0;
        totalPrice = 0;
        discountPercentage = 0;

        /**
         * set the property value
         * to the table columns
         */
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        /**
         * Initialize the observable list to
         * store all new orders
         * than set is to the table
         */
        orders = FXCollections.observableArrayList();
        tableOrders.setItems(orders);

        /**
         * add listeners to all TextFields to
         * update the local variables
         */
        txtDiscount.textProperty().addListener((v, oldValue, newValue) -> {
            try {
                discount = Double.parseDouble(newValue);
                txtDiscountPercentage.setText(((Double) ((discount * 100) / realPrice)).toString());
                //txtDiscountPercentage.setText(String.format("%d", ((discount * 100) / realPrice)));
                txtTotal.setText(((Double) (realPrice - discount)).toString());
            }
            catch (Exception ex){
                txtDiscount.setText("0");
                return;
            }
        });
        txtRealPrice.textProperty().addListener((v, oldVale, newValue) ->
                realPrice = Double.parseDouble(newValue));
        txtTotal.textProperty().addListener((v, oldValue, newValue) ->
                totalPrice = Double.parseDouble(newValue));
        txtDiscountPercentage.textProperty().addListener((v, oldValue, newValue) ->
                discountPercentage = Double.parseDouble(newValue));
    }

    /**
     * ##########################################################
     * ################ Event Handling Methods ##################
     * ##########################################################
     */

    // Button "btnAddOrder" event OnAction
    public void btnAddOrderOnClick(){
        orders.add(new OrderView(txtItemToAdd.getText(), Integer.parseInt(txtCountToAdd.getText()),
                Double.parseDouble(txtPriceToAdd.getText()), Integer.parseInt(txtCodeToAdd.getText())));
        ordersViewChanged();
    }

    public void ordersViewChanged(){
        Double totalPrice = 0d;
        for(OrderView ov : orders){
            totalPrice += ov.getPrice();
        }
        txtRealPrice.setText(totalPrice.toString());
    }
}
