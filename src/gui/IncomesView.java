package gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Created by Ahmed ali on 09/02/2016.
 */
public class IncomesView {
    private SimpleIntegerProperty id;
    private SimpleStringProperty details;
    private SimpleDoubleProperty value;
    private SimpleBooleanProperty isDebt;
    private SimpleStringProperty date;

    public IncomesView(int id, String details, double value, LocalDate date) {
        this.id = new SimpleIntegerProperty(id);
        this.details = new SimpleStringProperty(details);
        this.value = new SimpleDoubleProperty(value);
        this.date = new SimpleStringProperty(date.toString());
    }

    public IncomesView(String details, double value, boolean isDebt, String date) {
        this.details = new SimpleStringProperty(details);
        this.value = new SimpleDoubleProperty(value);
        this.isDebt = new SimpleBooleanProperty(isDebt);
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

    public String getDetails() {
        return details.get();
    }

    public SimpleStringProperty detailsProperty() {
        return details;
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public double getValue() {
        return value.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
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

    public boolean getIsDebt() {
        return isDebt.get();
    }

    public SimpleBooleanProperty isDebtProperty() {
        return isDebt;
    }

    public void setIsDebt(boolean isDebt) {
        this.isDebt.set(isDebt);
    }
}
