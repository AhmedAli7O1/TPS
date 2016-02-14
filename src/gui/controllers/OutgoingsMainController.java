package gui.controllers;

import core.Outgoing;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import gui.GuiMain;
import gui.Main;
import gui.OutgoingsView;
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

public class OutgoingsMainController {
    private ObservableList<OutgoingsView> outgoingsList;        //current outgoings list
    private TpsWindowController parent;

    @FXML private DatePicker dpOutgoings;
    @FXML private ComboBox<String> cboxOutgoingsViewStyle;
    @FXML private TextField txtTotalOutgoings;
    @FXML private TableView<OutgoingsView> tableOutgoings;            //Table : Outgoings
    @FXML private TableColumn<OutgoingsView, Integer> columnId;       //Column : ID
    @FXML private TableColumn<OutgoingsView, String> columnDetails;   //Column : Details
    @FXML private TableColumn<OutgoingsView, Double> columnValue;     //Column : Value
    @FXML private TableColumn<OutgoingsView, String> columnDate;      //Column : Date

    public void init() {
        parent = GuiMain.getTpsWindowController();

        dpOutgoings.setValue(LocalDate.now());
        cboxOutgoingsViewStyle.setItems(parent.getDataViewStyleList());
        cboxOutgoingsViewStyle.setValue(parent.getDataViewStyleList().get(1));

        // init Sales Table
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        outgoingsList = FXCollections.observableArrayList();

        tableOutgoings.setItems(outgoingsList);

        /**
         * bind txtTotalOutgoings which display Total Outgoings
         * of the table Outgoings
         * that means whenever the table updates the TextField
         * will update too
         */
        DoubleBinding total = Bindings.createDoubleBinding(() ->
                        tableOutgoings.getItems().stream().collect(Collectors.summingDouble(OutgoingsView::getValue)),
                tableOutgoings.getItems()
        );

        txtTotalOutgoings.textProperty().bind(Bindings.format("%3.2f", total));
    }

    @FXML //view Sales depends on the chosen date and style
    public void btnOutgoingsViewOnAction(){

        parent.showLoading();
        outgoingsList.clear();

        new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    // get outgoings
                    List<Outgoing> outgoings =
                            GuiMain.getOutgoingsControl().getOutgoings(dpOutgoings.getValue(),
                                    parent.getDataViewStyle(cboxOutgoingsViewStyle));

                    /**
                     * update observable list
                     * that will update Outgoings
                     * Table and total label too
                      */
                    Platform.runLater(() -> {
                        outgoingsList.addAll(outgoings.stream().map(outgoing -> new OutgoingsView(
                                outgoing.getId(),
                                outgoing.getDetails(),
                                outgoing.getValue(),
                                outgoing.getDate()
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
