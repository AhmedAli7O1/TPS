package core;

import java.time.LocalDate;

/**
 * Created by Ahmed Ali on 16/02/2016.
 */
public class Account {
    private int id;
    private double sales;
    private double debts;
    private double incomes;
    private double outgoings;
    private double withdrawals;
    private double purchases;
    private double balance;
    private double profits;
    private LocalDate date;

    public Account(int id, double sales,
                   double debts, double incomes,
                   double outgoings, double withdrawals,
                   double purchases, double balance,
                   double profits, LocalDate date) {
        this.id = id;
        this.sales = sales;
        this.debts = debts;
        this.incomes = incomes;
        this.outgoings = outgoings;
        this.withdrawals = withdrawals;
        this.purchases = purchases;
        this.balance = balance;
        this.profits = profits;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public double getSales() {
        return sales;
    }

    public double getDebts() {
        return debts;
    }

    public double getIncomes() {
        return incomes;
    }

    public double getOutgoings() {
        return outgoings;
    }

    public double getWithdrawals() {
        return withdrawals;
    }

    public double getPurchases() {
        return purchases;
    }

    public double getOldBalance() {
        //calc the actual balance
        double acBalance = ((sales + incomes) - (outgoings + withdrawals));
        //subtract from stored balance
        return (this.balance - acBalance);
    }

    public double getBalance() {
        return balance;
    }

    public double getProfits() {
        return profits;
    }

    public LocalDate getDate() {
        return date;
    }
}
