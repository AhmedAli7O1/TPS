package core;

import java.time.LocalDate;

public class Outgoing {
    private int id;
    private String details;
    private double value;
    private LocalDate date;
    private int accountId;

    public Outgoing(int id, String details, double value, LocalDate date, int accountId) {
        this.id = id;
        this.details = details;
        this.value = value;
        this.date = date;
        this.accountId = accountId;
    }

    public Outgoing(String details, double value, LocalDate date, int accountId) {
        this.details = details;
        this.value = value;
        this.date = date;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
