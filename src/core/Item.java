package core;

public class Item {
    private int id;
    private String name;
    private int amount;
    private double price;
    private double paid;
    private double purchaseValue;
    private int orderId;

    public Item(
            int id, String name, int amount,
            double price, double paid,
            double purchaseValue, int orderId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.paid = paid;
        this.purchaseValue = purchaseValue;
        this.orderId = orderId;
    }

    public Item(
            String name, int amount, double price, double paid) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.paid = paid;
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

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
