package gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class SalesView {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty amount;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty paid;
    private SimpleDoubleProperty purchaseValue;
    private SimpleStringProperty date;

    public SalesView(
            int id, String name,
            int amount, double price,
            double paid, double purchaseValue,
            String date) {

        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.price = new SimpleDoubleProperty(price);
        this.paid = new SimpleDoubleProperty(paid);
        this.purchaseValue = new SimpleDoubleProperty(purchaseValue);
        this.date = new SimpleStringProperty(date);
    }

    public SalesView(String name, int amount,
                     double price, double paid, double purchaseValue) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.price = new SimpleDoubleProperty(price);
        this.paid = new SimpleDoubleProperty(paid);
        this.purchaseValue = new SimpleDoubleProperty(purchaseValue);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public double getPaid() {
        return paid.get();
    }

    public SimpleDoubleProperty paidProperty() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid.set(paid);
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

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getPurchaseValue() {
        return purchaseValue.get();
    }

    public SimpleDoubleProperty purchaseValueProperty() {
        return purchaseValue;
    }

    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue.set(purchaseValue);
    }
}