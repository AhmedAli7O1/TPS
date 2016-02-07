package core;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private String customer;
    private double discount;
    private double totalPrice;
    private double paid;
    private LocalDate date;
    private List<Item> items;

    public Order(int id, String customer, double discount, double totalPrice, double paid, LocalDate date, List<Item> items) {
        this.id = id;
        this.customer = customer;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.paid = paid;
        this.date = date;
        this.items = items;
    }

    public Order(String customer, double discount, double totalPrice, double paid, LocalDate date, List<Item> items) {
        this.customer = customer;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.paid = paid;
        this.date = date;
        this.items = items;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
}
