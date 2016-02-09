package gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderView {
    private SimpleStringProperty item;
    private SimpleIntegerProperty amount;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty discount;
    private SimpleDoubleProperty soldPrice;
    private SimpleDoubleProperty paid;
    private SimpleIntegerProperty code;

    public OrderView(
            String item,
            int amount,
            Double price,
            Double discount,
            Double soldPrice,
            Double paid,
            int code) {
        this.item = new SimpleStringProperty(item);
        this.amount = new SimpleIntegerProperty(amount);
        this.price = new SimpleDoubleProperty(price);
        this.discount = new SimpleDoubleProperty(discount);
        this.soldPrice = new SimpleDoubleProperty(soldPrice);
        this.paid = new SimpleDoubleProperty(paid);
        this.code = new SimpleIntegerProperty(code);
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

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getDiscount() {
        return discount.get();
    }

    public SimpleDoubleProperty discountProperty() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    public double getSoldPrice() {
        return soldPrice.get();
    }

    public SimpleDoubleProperty soldPriceProperty() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice.set(soldPrice);
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

    public int getCode() {
        return code.get();
    }

    public SimpleIntegerProperty codeProperty() {
        return code;
    }

    public void setCode(int code) {
        this.code.set(code);
    }
}