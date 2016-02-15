package gui.controllers;

import core.Income;
import gui.GuiMain;
import gui.IncomesView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Ahmed Ali on 15/02/2016.
 */
public class AddIncomesController implements Initializable {

    @FXML private TextField txtDetails;
    @FXML private TextField txtValue;
    @FXML private DatePicker dpDate;
    @FXML private CheckBox ckIsDebt;
    @FXML private TableView<IncomesView> tableIncomes;
    @FXML private TableColumn<IncomesView, String> columnDetails;
    @FXML private TableColumn<IncomesView, Double> columnValue;
    @FXML private TableColumn<IncomesView, Integer> columnIsDebt;
    @FXML private TableColumn<IncomesView, String> columnDate;

    private ObservableList<IncomesView> newIncomesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnIsDebt.setCellValueFactory(new PropertyValueFactory<>("isDebt"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        newIncomesList = FXCollections.observableArrayList();
        tableIncomes.setItems(newIncomesList);
    }

    @FXML
    public void btnAddToTableOnAction(){
        newIncomesList.add(
                new IncomesView(
                        txtDetails.getText(),
                        Double.parseDouble(txtValue.getText()),
                        (ckIsDebt.isSelected() ? 1 : 0),
                        dpDate.getValue().toString()
                )
        );
    }

    @FXML
    public void btnSaveOnAction(){
        List<Income> incomes = newIncomesList.stream().map(incomeView -> new Income(
                incomeView.getDetails(),
                incomeView.getValue(),
                incomeView.getIsDebt(),
                LocalDate.parse(incomeView.getDate())
        )).collect(Collectors.toList());

        GuiMain.getIncomesControl().addIncomes(incomes);

        try{
            GuiMain.getIncomesControl().syncNewIncomes();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        GuiMain.getAddIncomesWindow().close();
    }
}
