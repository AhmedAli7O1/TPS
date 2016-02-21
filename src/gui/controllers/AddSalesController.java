package gui.controllers;

import core.Item;
import core.Order;
import gui.GuiMain;
import gui.SalesView;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddSalesController implements Initializable {
    @FXML private TextField txtCustomer;
    @FXML private DatePicker dpOrderDate;
    @FXML private TextField txtItemToAdd;
    @FXML private TextField txtCountToAdd;
    @FXML private TextField txtPriceToAdd;
    @FXML private TableView<SalesView> tableOrders;
    @FXML private TextField txtTotalPrice;
    @FXML private TextField txtTotalPaid;
    @FXML private TextField txtTotalRemaining;
    @FXML private TableColumn<SalesView, String> cnItem;
    @FXML private TableColumn<SalesView, String> cnAmount;
    @FXML private TableColumn<SalesView, String> cnPrice;
    @FXML private TableColumn<SalesView, String> cnPaid;
    @FXML private ImageView imageAddSalesSave;
    @FXML private ImageView imageAddSalesDelete;
    @FXML private ImageView imageMoney;
    @FXML private ImageView imageInvoice;
    @FXML private TextField txtPaidToAdd;


    private ObservableList<SalesView> orders;

    private Double totalPrice = 0d;
    private Double totalPaid = 0d;
    private Double totalRemaining = 0d;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imageAddSalesSave.setImage(new Image(GuiMain.class.getResourceAsStream("add_16x16.png")));
        imageAddSalesDelete.setImage(new Image(GuiMain.class.getResourceAsStream("delete_16x16.png")));
        imageMoney.setImage(new Image(GuiMain.class.getResourceAsStream("money_64x64.png")));
        imageInvoice.setImage(new Image(GuiMain.class.getResourceAsStream("invoice_64x64.png")));

        /**
         * set the property value
         * to the table columns
         */
        cnItem.setCellValueFactory(new PropertyValueFactory<>("name"));
        cnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        cnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cnPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));

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
        int amount = Integer.parseInt(txtCountToAdd.getText());
        double price = Double.parseDouble(txtPriceToAdd.getText());
        double paid = Double.parseDouble(txtPaidToAdd.getText());

        orders.add(new SalesView(
                itemName,           // item name
                amount,              // count
                price,              // price
                paid,               // Paid
                0));             // PurchaseValue

        txtItemToAdd.clear();
        txtCountToAdd.clear();
        txtPriceToAdd.clear();
        txtPaidToAdd.clear();

        ordersViewChanged();
    }

    public void ordersViewChanged(){
        totalPrice = 0d;
        totalPaid = 0d;
        totalRemaining = 0d;

        for(SalesView ov : orders){
            totalPrice += ov.getPrice();
            totalPaid += ov.getPaid();
        }

        totalRemaining = totalPrice - totalPaid;

        txtTotalPrice.setText(totalPrice.toString());
        txtTotalPaid.setText(totalPaid.toString());
        txtTotalRemaining.setText(totalRemaining.toString());
    }

    @FXML
    public void btnSaveOrderOnAction(){

        List<Item> items = orders.stream().map(salesView -> new Item(
                salesView.getName(),
                salesView.getAmount(),
                salesView.getPrice(),
                salesView.getPaid()          //paid
        )).collect(Collectors.toList());


        GuiMain.getSalesControl().addOrder(new Order(
                txtCustomer.getText(),
                Double.parseDouble(txtTotalPrice.getText()),
                Double.parseDouble(txtTotalPaid.getText()),
                dpOrderDate.getValue(),
                items,
                GuiMain.getAccountControl().getAccountId(dpOrderDate.getValue())
        ));

        // save changes made to sales list
        try {
            GuiMain.getSalesControl().addNewOrders();
        }catch (Exception ex){}

        GuiMain.getAddSalesWindow().close();
    }
}
