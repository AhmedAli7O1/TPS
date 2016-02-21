package gui.controllers;

import gui.GuiMain;
import gui.PurchasesView;
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
import core.Purchase;
import java.time.LocalDate;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Ahmed Ali on 21/02/2016.
 */
public class AddPurchasesController implements Initializable {

    @FXML private TextField txtItem;
    @FXML private DatePicker dpDate;
    @FXML private TextField txtSeller;
    @FXML private TextField txtAmount;
    @FXML private TextField txtPrice;
    @FXML private TableView<PurchasesView> tablePurchases;
    @FXML private TableColumn<PurchasesView, String> cnItems;
    @FXML private TableColumn<PurchasesView, String> cnAmount;
    @FXML private TableColumn<PurchasesView, String> cnPrice;
    @FXML private TableColumn<PurchasesView, String> cnSeller;
    @FXML private TableColumn<PurchasesView, String> cnDate;
    @FXML private ImageView imageSave;
    @FXML private ImageView imageCancel;

    private ObservableList<PurchasesView> purchases;

    //inputs
    private String item;
    private int amount;
    private double price;
    private String seller;
    private String date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageSave.setImage(new Image(GuiMain.class.getResourceAsStream("save_32x32.png")));
        imageCancel.setImage(new Image(GuiMain.class.getResourceAsStream("cancel_32x32.png")));

        dpDate.setValue(LocalDate.now());

        // initialize TableView
        cnItems.setCellValueFactory(new PropertyValueFactory<>("item"));
        cnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        cnPrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        cnSeller.setCellValueFactory(new PropertyValueFactory<>("seller"));
        cnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        purchases = FXCollections.observableArrayList();
        tablePurchases.setItems(purchases);


    }

    @FXML
    public void btnAddToTableOnAction(){
        if(!validateInputs())
            return;

        purchases.add(
                new PurchasesView(
                    item, amount, price, seller, date
                )
        );
    }

    private boolean validateInputs(){
        item = txtItem.getText();
        amount = Integer.parseInt(txtAmount.getText());
        price = Double.parseDouble(txtPrice.getText());
        seller = txtSeller.getText();
        date = dpDate.getValue().toString();

        txtItem.clear();
        txtAmount.clear();
        txtPrice.clear();
        txtSeller.clear();
        return true;
    }

    @FXML
    public void btnSaveOnAction(){
        GuiMain.getPurchasesControl().addPurchases(
                purchases.stream().map(purch -> new Purchase(
                        purch.getItem(),
                        purch.getAmount(),
                        purch.getPurchasePrice(),
                        purch.getSeller(),
                        LocalDate.parse(purch.getDate()),
                        GuiMain.getAccountControl().getAccountId(LocalDate.parse(purch.getDate()))
                )).collect(Collectors.toList())
        );

        try{
            GuiMain.getPurchasesControl().saveChanges();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            GuiMain.getAddPurchasesWindow().close();
        }
    }
}
