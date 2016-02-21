package gui.controllers;

import core.UpdateType;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import gui.ChartSeries;
import gui.GuiMain;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public class StatisticsMainController {
    @FXML private BarChart<String, Number> chart;
    @FXML private ChoiceBox<Integer> cbYear;
    @FXML private ChoiceBox<Integer> cbMonth;
    @FXML private Label lblYearTotalSales;
    @FXML private Label lblMonthTotalSales;
    @FXML private Label lblMonthTotalIncomes;
    @FXML private Label lblMonthTotalPurchases;
    @FXML private Label lblMonthTotalOutgoings;
    @FXML private Label lblMonthTotalWithdrawals;
    @FXML private Label lblMonthTotalBalance;
    @FXML private Label lblMonthTotalDebts;
    @FXML private Label lblMonthTotalProfits;

    private ObservableList<Integer> availableYears;
    private ObservableList<Integer> availableMonths;

    TpsWindowController parent;

    public void init(){
        parent = GuiMain.getTpsWindowController();
        availableYears = FXCollections.observableArrayList();
        availableMonths = FXCollections.observableArrayList();

        cbYear.setItems(availableYears);
        cbMonth.setItems(availableMonths);

        availableYears.addAll(
                GuiMain.getAccountControl()
                        .getAvailableYears()
        );

        cbYear.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue) -> {
            availableMonths.addAll(
                    GuiMain.getAccountControl().getAvailableMonths(newValue)
            );
        });

        if(availableYears.size() > 0) {
            cbYear.getSelectionModel().select(
                    availableYears.get(availableYears.size() - 1));   //by default select last year
            cbMonth.getSelectionModel().select(
                    availableMonths.get(availableMonths.size() - 1)); //by default select last month
        }
    }

    @FXML
    public void btnViewOnAction(){
        setChart();
    }

    private void setChart(){

        parent.showLoading();

        new Thread(
                new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try{
                            ChartSeries chartSeries = new ChartSeries(
                                    GuiMain.getAccountControl().getAccounts(UpdateType.FORCE_UPDATE));

                            Platform.runLater(() -> {
                                chart.setData(chartSeries.getSeriesList());
                                lblMonthTotalSales.setText(
                                        GuiMain.getAccountControl().getMonthTotalSales(
                                                cbMonth.getValue(), cbYear.getValue()).toString()
                                );

                                lblMonthTotalIncomes.setText(
                                        GuiMain.getAccountControl().getMonthTotalIncomes(
                                                cbMonth.getValue(), cbYear.getValue()
                                        ).toString()
                                );

                                lblMonthTotalPurchases.setText(
                                        GuiMain.getAccountControl().getMonthTotalPurchases(
                                                cbMonth.getValue(), cbYear.getValue()
                                        ).toString()
                                );

                                lblMonthTotalOutgoings.setText(
                                        GuiMain.getAccountControl().getMonthTotalOutgoings(
                                                cbMonth.getValue(), cbYear.getValue()
                                        ).toString()
                                );

                                lblMonthTotalWithdrawals.setText(
                                        GuiMain.getAccountControl().getMonthTotalWithdrawals(
                                                cbMonth.getValue(), cbYear.getValue()
                                        ).toString()
                                );

                                lblMonthTotalBalance.setText(
                                        GuiMain.getAccountControl().getMonthBalance(
                                                cbMonth.getValue(), cbYear.getValue()
                                        ).toString()
                                );

                                lblMonthTotalDebts.setText(
                                        GuiMain.getAccountControl().getMonthTotalDebts(
                                                cbMonth.getValue(), cbYear.getValue()
                                        ).toString()
                                );

                                lblMonthTotalProfits.setText(
                                        GuiMain.getAccountControl().getMonthProfits(
                                                cbMonth.getValue(), cbYear.getValue()
                                        ).toString()
                                );

                                lblYearTotalSales.setText(
                                        GuiMain.getAccountControl().getYearTotalSales().toString());
                            });

                        }catch (WSConnException ex){
                            //TODO: add no connection GUI alert
                            System.out.println("no connection");
                        }catch(NoDataException ex){
                            //TODO: add no Data GUI alert
                            System.out.println("no data received");
                        } finally {
                            parent.hideLoading(); // hide loading pane
                        }
                        return null;
                    }
                }
        ).start();
    }

}
