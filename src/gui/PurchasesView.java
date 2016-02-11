package gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public class PurchasesView {
    private SimpleIntegerProperty id;
    private SimpleStringProperty item;
    private SimpleIntegerProperty amount;
    private SimpleDoubleProperty purchasePrice;
    private SimpleStringProperty seller;
    private SimpleStringProperty date;

    public PurchasesView(int id, String item, int amount, double purchasePrice, String seller, String date) {
        this.id = new SimpleIntegerProperty(id);
        this.item = new SimpleStringProperty(item);
        this.amount = new SimpleIntegerProperty(amount);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.seller = new SimpleStringProperty(seller);
        this.date = new SimpleStringProperty(date);
    }

    public PurchasesView(String item, int amount, double purchasePrice, String seller, String date) {
        this.id = new SimpleIntegerProperty();
        this.item = new SimpleStringProperty(item);
        this.amount = new SimpleIntegerProperty(amount);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.seller = new SimpleStringProperty(seller);
        this.date = new SimpleStringProperty(date);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getItem() {
        return item.get();
    }

    public SimpleStringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public double getPurchasePrice() {
        return purchasePrice.get();
    }

    public SimpleDoubleProperty purchasePriceProperty() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice.set(purchasePrice);
    }

    public String getSeller() {
        return seller.get();
    }

    public SimpleStringProperty sellerProperty() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller.set(seller);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
