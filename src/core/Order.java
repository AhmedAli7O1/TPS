package core;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;
    private String customer;
    private double price;
    private double paid;
    private LocalDate date;
    private List<Item> items;
    private int accountId;

    public Order(int id, String customer,
                 double price, double paid, LocalDate date,
                 List<Item> items, int accountId) {
        this.id = id;
        this.customer = customer;
        this.price = price;
        this.paid = paid;
        this.date = date;
        this.items = items;
        this.accountId = accountId;
    }

    public Order(String customer,
                 double price, double paid, LocalDate date,
                 List<Item> items, int accountId) {
        this.customer = customer;
        this.price = price;
        this.paid = paid;
        this.date = date;
        this.items = items;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double totalPrice) {
        this.price = totalPrice;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
