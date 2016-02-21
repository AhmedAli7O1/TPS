package core;

import java.time.LocalDate;

public class Income {
    private int id;
    private String details;
    private double value;
    private LocalDate date;
    private boolean isDebt;
    private int accountId;

    public Income(int id, String details, double value, boolean isDebt, LocalDate date, int accountId) {
        this.id = id;
        this.details = details;
        this.value = value;
        this.isDebt = isDebt;
        this.date = date;
        this.accountId = accountId;
    }

    public Income(String details, double value, boolean isDebt, LocalDate date, int accountId) {
        this.details = details;
        this.value = value;
        this.isDebt = isDebt;
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

    public boolean getIsDebt() {
        return isDebt;
    }

    public void setIsDebt(boolean isDebt) {
        this.isDebt = isDebt;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
