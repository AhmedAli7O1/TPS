package core;

import java.time.LocalDate;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public class Purchase {
    private int id;
    private String item;
    private int amount;
    private double purchasePrice;
    private String seller;
    private LocalDate date;

    public Purchase(int id, String item, int amount, double purchasePrice, String seller, LocalDate date) {
        this.id = id;
        this.item = item;
        this.amount = amount;
        this.purchasePrice = purchasePrice;
        this.seller = seller;
        this.date = date;
    }

    public Purchase(String item, int amount, double purchasePrice, String seller, LocalDate date) {
        this.item = item;
        this.amount = amount;
        this.purchasePrice = purchasePrice;
        this.seller = seller;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
