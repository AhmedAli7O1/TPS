package gui.sales;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class SalesView {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty amount;
    private SimpleDoubleProperty soldPrice;
    private SimpleDoubleProperty paid;
    private SimpleStringProperty date;

    public SalesView(
            int id, String name,
            int amount, double soldPrice,
            double paid, String date) {

        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.soldPrice = new SimpleDoubleProperty(soldPrice);
        this.paid = new SimpleDoubleProperty(paid);
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

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public static Double getTotalPaid(List<SalesView> sales){
        double total = 0;
        for (SalesView sale : sales){
            total += sale.getPaid();
        }
        return total;
    }
}