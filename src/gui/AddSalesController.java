package gui;

import core.Item;
import core.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSalesController extends Main implements Initializable{
    @FXML private TextField txtCustomer;
    @FXML private TextField txtPaid;
    @FXML private DatePicker dpOrderDate;
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
    @FXML private TableColumn<OrderView, String> discountColumn;
    @FXML private TableColumn<OrderView, String> soldPriceColumn;
    @FXML private TableColumn<OrderView, String> paidColumn;
    @FXML private TableColumn<OrderView, String> codeColumn;
    @FXML private ImageView imageAddSalesSave;
    @FXML private ImageView imageAddSalesDelete;
    @FXML private ImageView imageMoney;
    @FXML private ImageView imageInvoice;
    @FXML private TextField txtDiscountToAdd;
    @FXML private TextField txtSoldPriceToAdd;
    @FXML private TextField txtPaidToAdd;
    @FXML private TextField txtRemaining;

    private ObservableList<OrderView> orders;

    private Double totalPrice = 0d;
    private Double totalDiscount = 0d;
    private Double totalSoldPrice = 0d;
    private Double totalPaid = 0d;
    private Double discountPercentage = 0d;
    private Double remaining = 0d;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imageAddSalesSave.setImage(new Image(getClass().getResourceAsStream("add_16x16.png")));
        imageAddSalesDelete.setImage(new Image(getClass().getResourceAsStream("delete_16x16.png")));
        imageMoney.setImage(new Image(getClass().getResourceAsStream("money_64x64.png")));
        imageInvoice.setImage(new Image(getClass().getResourceAsStream("invoice_64x64.png")));

        /**
         * set the property value
         * to the table columns
         */
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        soldPriceColumn.setCellValueFactory(new PropertyValueFactory<>("soldPrice"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        /**
         * Initialize the observable list to
         * store all new orders
         * than set is to the table
         */
        orders = FXCollections.observableArrayList();
        tableOrders.setItems(orders);

    }

    /**
     * ##########################################################
     * ################ Event Handling Methods ##################
     * ##########################################################
     */

    // Button "btnAddOrder" event OnAction
    public void btnAddOrderOnClick(){
        String itemName = txtItemToAdd.getText();
        int count = Integer.parseInt(txtCountToAdd.getText());
        double price = Double.parseDouble(txtPriceToAdd.getText());
        double discount = Double.parseDouble(txtDiscountToAdd.getText());
        double soldPrice = Double.parseDouble(txtSoldPriceToAdd.getText());
        double paid = Double.parseDouble(txtPaidToAdd.getText());
        int code = Integer.parseInt(txtCodeToAdd.getText());

        try {
            code = Integer.parseInt(txtCodeToAdd.getText());
        }catch (Exception ex){

        }

        orders.add(new OrderView(
                itemName,           // item name
                count,              // count
                price,              // price
                discount,           // discount
                soldPrice,          // Sold Price
                paid,               // Paid
                code));             // code

        txtItemToAdd.clear();
        txtCountToAdd.clear();
        txtPriceToAdd.clear();
        txtDiscountToAdd.clear();
        txtSoldPriceToAdd.clear();
        txtPaidToAdd.clear();
        txtCodeToAdd.clear();

        ordersViewChanged();
    }

    public void ordersViewChanged(){
        totalPrice = 0d;
        totalDiscount = 0d;
        totalSoldPrice = 0d;
        totalPaid = 0d;
        discountPercentage = 0d;
        remaining = 0d;

        for(OrderView ov : orders){
            totalPrice += ov.getPrice();
            totalDiscount += ov.getDiscount();
            totalSoldPrice += ov.getSoldPrice();
            totalPaid += ov.getPaid();
        }

        discountPercentage = (totalDiscount * (100 / totalPrice));
        remaining = totalSoldPrice - totalPaid;

        txtRealPrice.setText(totalPrice.toString());
        txtDiscount.setText(totalDiscount.toString());
        txtTotal.setText(totalSoldPrice.toString());
        txtPaid.setText(totalPaid.toString());
        txtDiscountPercentage.setText(discountPercentage.toString());
        txtRemaining.setText(remaining.toString());
    }

    @FXML
    private void btnSaveOrderOnAction(){

        List<Item> items = new ArrayList<>();
        for(OrderView orderView : orders){
            items.add(new Item(
                    orderView.getItem(),
                    orderView.getAmount(),
                    orderView.getPrice(),
                    orderView.getDiscount(),      //discount
                    orderView.getSoldPrice(),     //sold price
                    orderView.getPaid(),          //paid
                    dpOrderDate.getValue()        // date
            ));
        }

        salesControl.addOrder(new Order(
                txtCustomer.getText(),
                Double.parseDouble(txtDiscount.getText()),
                Double.parseDouble(txtTotal.getText()),
                Double.parseDouble(txtPaid.getText()),
                dpOrderDate.getValue(),
                items
        ));
        addSalesWindow.close();
    }
}
