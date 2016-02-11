package gui;

import core.Purchase;
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
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PurchasesMainController {
    private ObservableList<PurchasesView> purchasesList;        //current purchases list
    private IParentController parent;                           //link to MainController instance

    @FXML private DatePicker dpDate;
    @FXML private ComboBox<String> cbViewStyle;
    @FXML private TextField txtTotalPurchases;
    @FXML private TableView<PurchasesView> tablePurchases;                  //Table : Purchases
    @FXML private TableColumn<PurchasesView, Integer> columnId;             //Column : ID
    @FXML private TableColumn<PurchasesView, String> columnItem;            //Column : Item
    @FXML private TableColumn<PurchasesView, Integer> columnAmount;         //Column : Amount
    @FXML private TableColumn<PurchasesView, Double> columnPurchasesPrice;  //Column : Purchases Price
    @FXML private TableColumn<PurchasesView, String> columnSeller;          //Column : Seller
    @FXML private TableColumn<PurchasesView, String> columnDate;            //Column : Date

    //MainController calls this method to set the parent interface
    public void setMainController(IParentController parent){
        this.parent = parent;
        dpDate.setValue(LocalDate.now());
        cbViewStyle.setItems(parent.getDataViewStyleList());
        cbViewStyle.setValue(parent.getDataViewStyleList().get(1));

        // init Sales Table
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnPurchasesPrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        columnSeller.setCellValueFactory(new PropertyValueFactory<>("seller"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        purchasesList = FXCollections.observableArrayList();

        tablePurchases.setItems(purchasesList);

        /**
         * bind txtTotalPurchases which display Total Purchases
         * of the table Purchases
         * that means whenever the table updates the TextField
         * will update too
         */
        DoubleBinding total = Bindings.createDoubleBinding(() ->
                        tablePurchases.getItems().stream().collect(Collectors.summingDouble(PurchasesView::getPurchasePrice)),
                tablePurchases.getItems()
        );

        txtTotalPurchases.textProperty().bind(Bindings.format("%3.2f", total));
    }

    @FXML //view Purchases depends on the chosen date and style
    public void btnViewOnAction(){

        parent.showLoading();
        purchasesList.clear();

        new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    // get purchases
                    List<Purchase> purchases =
                            parent.getPurchasesControl().getPurchases(dpDate.getValue(),
                                    parent.getDataViewStyle(cbViewStyle));

                    /**
                     * update observable list
                     * that will update Purchases
                     * Table and total label too
                     */
                    Platform.runLater(() -> {
                        purchasesList.addAll(purchases.stream().map(purchase -> new PurchasesView(
                                purchase.getId(),
                                purchase.getItem(),
                                purchase.getAmount(),
                                purchase.getPurchasePrice(),
                                purchase.getSeller(),
                                purchase.getDate().toString()
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
        }).start();
    }
}
