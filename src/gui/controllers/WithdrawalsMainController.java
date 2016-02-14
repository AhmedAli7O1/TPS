package gui.controllers;

import core.Withdraw;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import gui.GuiMain;
import gui.Main;
import gui.WithdrawalsView;
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
public class WithdrawalsMainController {
    private ObservableList<WithdrawalsView> withdrawalsList;        //current outgoings list
    private TpsWindowController parent;

    @FXML private DatePicker dpWithdrawals;
    @FXML private ComboBox<String> cboxWithdrawalsViewStyle;
    @FXML private TextField txtTotalWithdrawals;
    @FXML private TableView<WithdrawalsView> tableWithdrawals;            //Table : Outgoings
    @FXML private TableColumn<WithdrawalsView, Integer> columnId;       //Column : ID
    @FXML private TableColumn<WithdrawalsView, String> columnDetails;   //Column : Details
    @FXML private TableColumn<WithdrawalsView, Double> columnValue;     //Column : Value
    @FXML private TableColumn<WithdrawalsView, String> columnDate;      //Column : Date


    public void init() {
        parent = GuiMain.getTpsWindowController();

        dpWithdrawals.setValue(LocalDate.now());
        cboxWithdrawalsViewStyle.setItems(parent.getDataViewStyleList());
        cboxWithdrawalsViewStyle.setValue(parent.getDataViewStyleList().get(1));

        // init Sales Table
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        withdrawalsList = FXCollections.observableArrayList();

        tableWithdrawals.setItems(withdrawalsList);

        /**
         * bind txtTotalWithdrawals which display Total Withdrawals
         * of the table Withdrawals
         * that means whenever the table updates the TextField
         * will update too
         */
        DoubleBinding total = Bindings.createDoubleBinding(() ->
                        tableWithdrawals.getItems().stream().collect(Collectors.summingDouble(WithdrawalsView::getValue)),
                tableWithdrawals.getItems()
        );

        txtTotalWithdrawals.textProperty().bind(Bindings.format("%3.2f", total));
    }

    @FXML //view Sales depends on the chosen date and style
    public void btnWithdrawalsViewOnAction(){

        parent.showLoading();
        withdrawalsList.clear();

        new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    // get outgoings
                    List<Withdraw> withdrawals =
                            GuiMain.getWithdrawalsControl().getWithdrawals(dpWithdrawals.getValue(),
                                    parent.getDataViewStyle(cboxWithdrawalsViewStyle));

                    /**
                     * update observable list
                     * that will update Withdrawals
                     * Table and total label too
                     */
                    Platform.runLater(() -> {
                        withdrawalsList.addAll(withdrawals.stream().map(withdraw -> new WithdrawalsView(
                                withdraw.getId(),
                                withdraw.getDetails(),
                                withdraw.getValue(),
                                withdraw.getDate()
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
