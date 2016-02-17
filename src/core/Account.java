package core;

import java.time.LocalDate;

/**
 * Created by Ahmed Ali on 16/02/2016.
 */
public class Account {
    private int id;
    private double totalSales;
    private double totalIncomes;
    private double totalOutgoings;
    private double totalwithdrawals;
    private double totalPurchases;
    private LocalDate date;

    public Account(int id, double totalSales, double totalIncomes, double totalOutgoings, double totalwithdrawals, double totalPurchases, LocalDate date) {
        this.id = id;
        this.totalSales = totalSales;
        this.totalIncomes = totalIncomes;
        this.totalOutgoings = totalOutgoings;
        this.totalwithdrawals = totalwithdrawals;
        this.totalPurchases = totalPurchases;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public double getTotalIncomes() {
        return totalIncomes;
    }

    public double getTotalOutgoings() {
        return totalOutgoings;
    }

    public double getTotalwithdrawals() {
        return totalwithdrawals;
    }

    public double getTotalPurchases() {
        return totalPurchases;
    }

    public LocalDate getDate() {
        return date;
    }
}
