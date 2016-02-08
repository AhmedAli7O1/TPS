package core;

import java.time.LocalDate;

public class Outgoing {
    private int id;
    private String details;
    private double value;
    private LocalDate date;

    public Outgoing(int id, String details, double value, LocalDate date) {
        this.id = id;
        this.details = details;
        this.value = value;
        this.date = date;
    }

    public Outgoing(String details, double value, LocalDate date) {
        this.details = details;
        this.value = value;
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
}
