package core;

import org.joda.time.DateTime;

import java.time.LocalDate;

public class Item {
    private int id;
    private String name;
    private int amount;
    private double price;
    private double discount;
    private double soldPrice;
    private double purchaseValue;
    private double paid;
    private LocalDate date;

    public Item(int id, String name, int amount, double price, double discount, double soldPrice, double purchaseValue, double paid, LocalDate date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
        this.soldPrice = soldPrice;
        this.purchaseValue = purchaseValue;
        this.paid = paid;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }
}
