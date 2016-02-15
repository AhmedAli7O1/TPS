package core;

import java.time.LocalDate;

public class Income {
    private int id;
    private String details;
    private double value;
    private int isDebt;
    private LocalDate date;

    public Income(int id, String details, double value, int isDebt, LocalDate date) {
        this.id = id;
        this.details = details;
        this.value = value;
        this.isDebt = isDebt;
        this.date = date;
    }

    public Income(String details, double value, int isDebt, LocalDate date) {
        this.details = details;
        this.value = value;
        this.isDebt = isDebt;
        this.date = date;
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

    public int getIsDebt() {
        return isDebt;
    }

    public void setIsDebt(int isDebt) {
        this.isDebt = isDebt;
    }
}
