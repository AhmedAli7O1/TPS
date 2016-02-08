package gui.outgoings;

import core.Outgoing;
import gui.IParentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class OutgoingsMainController {
    private ObservableList<OutgoingsView> outgoingsList;        //current outgoings list
    private IParentController parent;                           //link to MainController instance

    @FXML private DatePicker dpOutgoings;
    @FXML private ComboBox<String> cboxOutgoingsViewStyle;
    @FXML private TextField txtTotalOutgoings;
    @FXML private TableView<OutgoingsView> tableOutgoings;            //Table : Outgoings
    @FXML private TableColumn<OutgoingsView, Integer> columnId;       //Column : ID
    @FXML private TableColumn<OutgoingsView, String> columnDetails;   //Column : Details
    @FXML private TableColumn<OutgoingsView, Double> columnValue;     //Column : Value
    @FXML private TableColumn<OutgoingsView, String> columnDate;      //Column : Date

    //MainController calls this method to set the parent interface
    public void setMainController(IParentController parent){
        this.parent = parent;
        cboxOutgoingsViewStyle.setItems(parent.getDataViewStyleList());
        cboxOutgoingsViewStyle.setValue(parent.getDataViewStyleList().get(1));

        // init Sales Table
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        outgoingsList = FXCollections.observableArrayList();

        tableOutgoings.setItems(outgoingsList);
    }

    @FXML //view Sales depends on the chosen date and style
    public void btnOutgoingsViewOnAction(){

        parent.showLoading();
        outgoingsList.clear();

        new Thread(
                () -> {
                    Double totalOutgoings = 0d;
                    try {
                        List<Outgoing> outgoings =
                                parent.getOutgoingsControl().getOutgoings(dpOutgoings.getValue(),
                                        parent.getDataViewStyle(cboxOutgoingsViewStyle));
                        for (Outgoing outgoing : outgoings) {
                            totalOutgoings += outgoing.getValue();
                            outgoingsList.add(new OutgoingsView(
                                    outgoing.getId(),
                                    outgoing.getDetails(),
                                    outgoing.getValue(),
                                    outgoing.getDate()
                            ));
                        }

                    }
                    catch(Exception ex){
                    }
                    finally {
                        txtTotalOutgoings.setText(totalOutgoings.toString());
                        parent.hideLoading();
                    }
                }
        ).start();
    }
}
