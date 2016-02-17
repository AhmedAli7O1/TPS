package gui;

import core.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.List;

/**
 * Created by Ahmed Ali on 16/02/2016.
 */
public class ChartSeries {
    private ObservableList<XYChart.Series<String, Number>> seriesList;
    private ObservableList<XYChart.Data<String, Number>> salesData;
    private ObservableList<XYChart.Data<String, Number>> incomesData;
    private ObservableList<XYChart.Data<String, Number>> outgoingsData;
    private ObservableList<XYChart.Data<String, Number>> purchasesData;

    public ChartSeries(List<Account> accounts){
        seriesList = FXCollections.observableArrayList();
        salesData = FXCollections.observableArrayList();
        incomesData = FXCollections.observableArrayList();
        outgoingsData = FXCollections.observableArrayList();
        purchasesData = FXCollections.observableArrayList();

        //get accounts data into their lists
        getData(accounts);

        //create Sales Series
        createSeries(salesData, "المبيعات");
        //create Incomes Series
        createSeries(incomesData, "الوارد");
        //create Outgoings Series
        createSeries(outgoingsData, "المصروفات");
        //create Purchases Series
        createSeries(purchasesData, "المشتريات");
    }

    public void createSeries(ObservableList<XYChart.Data<String, Number>> data, String name){
        XYChart.Series<String, Number> series = new XYChart.Series<>(data);
        series.setName(name);
        seriesList.add(series);
    }

    public ObservableList<XYChart.Series<String, Number>> getSeriesList() {
        return seriesList;
    }

    private void getData(List<Account> accounts){
        accounts.stream().forEach(account -> {
            salesData.add(new XYChart.Data<>(String.valueOf(account.getDate().getMonthValue()), account.getTotalSales()));
            incomesData.add(new XYChart.Data<>(String.valueOf(account.getDate().getMonthValue()), account.getTotalIncomes()));
            outgoingsData.add(new XYChart.Data<>(String.valueOf(account.getDate().getMonthValue()), account.getTotalOutgoings()));
            purchasesData.add(new XYChart.Data<>(String.valueOf(account.getDate().getMonthValue()), account.getTotalPurchases()));
        });
    }
}
