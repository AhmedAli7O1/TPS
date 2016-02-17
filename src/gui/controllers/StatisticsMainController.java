package gui.controllers;

import core.DataViewStyle;
import core.UpdateType;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import gui.ChartSeries;
import gui.GuiMain;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public class StatisticsMainController {
    @FXML private BarChart<String, Number> chart;
    @FXML private CategoryAxis categoryAxis;
    @FXML private NumberAxis numberAxis;
    @FXML private ChoiceBox<Integer> cbYear;
    @FXML private ChoiceBox<Integer> cbMonth;
    @FXML private ImageView imageYear;
    @FXML private ImageView imageMonth;
    @FXML private Label lblYearTotalSales;
    @FXML private Label lblMonthTotalSales;

    private ObservableList<Integer> availableYears;
    private ObservableList<Integer> availableMonths;

    TpsWindowController parent;

    public void init(){
        parent = GuiMain.getTpsWindowController();
        availableYears = FXCollections.observableArrayList();
        availableMonths = FXCollections.observableArrayList();

        imageYear.setImage(new Image(GuiMain.class.getResourceAsStream("statyear_64x64.png")));
        imageMonth.setImage(new Image(GuiMain.class.getResourceAsStream("statmonth_64x64.png")));

        cbYear.setItems(availableYears);
        cbMonth.setItems(availableMonths);

        availableYears.addAll(
                GuiMain.getAccountControl()
                        .getAvailableYears(UpdateType.UPDATE_IF_NO_DATA)
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
                                    GuiMain.getAccountControl().getAccounts(
                                            cbYear.getValue()
                                    )); // need to add list of accounts

                            Platform.runLater(() -> {
                                chart.setData(chartSeries.getSeriesList());
                                lblMonthTotalSales.setText(
                                        GuiMain.getAccountControl().getMonthTotalSales(cbMonth.getValue()).toString());
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
