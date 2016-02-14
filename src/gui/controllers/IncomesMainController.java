package gui.controllers;

import core.Income;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import gui.GuiMain;
import gui.IncomesView;
import gui.Main;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Ahmed ali on 09/02/2016.
 */

public class IncomesMainController {

    private ObservableList<IncomesView> incomesList;        //current outgoings list
    private TpsWindowController parent;

    @FXML private DatePicker dpIncomes;
    @FXML private ComboBox<String> cboxIncomesViewStyle;
    @FXML private TextField txtTotalIncomes;
    @FXML private TableView<IncomesView> tableIncomes;            //Table : Outgoings
    @FXML private TableColumn<IncomesView, Integer> columnId;       //Column : ID
    @FXML private TableColumn<IncomesView, String> columnDetails;   //Column : Details
    @FXML private TableColumn<IncomesView, Double> columnValue;     //Column : Value
    @FXML private TableColumn<IncomesView, String> columnDate;      //Column : Date

    public void init() {
        parent = GuiMain.getTpsWindowController();
        dpIncomes.setValue(LocalDate.now());
        cboxIncomesViewStyle.setItems(parent.getDataViewStyleList());
        cboxIncomesViewStyle.setValue(parent.getDataViewStyleList().get(1));

        // init Sales Table
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        incomesList = FXCollections.observableArrayList();

        tableIncomes.setItems(incomesList);

        /**
         * bind txtTotalIncomes which display Total Incomes
         * of the table Incomes
         * that means whenever the table updates the TextField
         * will update too
         */
        DoubleBinding total = Bindings.createDoubleBinding(() ->
                        tableIncomes.getItems().stream().collect(Collectors.summingDouble(IncomesView::getValue)),
                tableIncomes.getItems()
        );

        txtTotalIncomes.textProperty().bind(Bindings.format("%3.2f", total));
    }

    @FXML //view Sales depends on the chosen date and style
    public void btnIncomesViewOnAction(){

        parent.showLoading();
        incomesList.clear();

        new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    // get outgoings
                    List<Income> incomes =
                            GuiMain.getIncomesControl().getIncomes(dpIncomes.getValue(),
                                    parent.getDataViewStyle(cboxIncomesViewStyle));

                    /**
                     * update observable list
                     * that will update Incomes
                     * Table and total label too
                     */
                    Platform.runLater(() -> {
                        incomesList.addAll(incomes.stream().map(income -> new IncomesView(
                                income.getId(),
                                income.getDetails(),
                                income.getValue(),
                                income.getDate()
                        )).collect(Collectors.toList()));
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
        }).start();
    }

}
