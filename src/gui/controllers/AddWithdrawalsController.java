package gui.controllers;

import gui.GuiMain;
import gui.WithdrawalsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import core.Withdraw;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Ahmed Ali on 21/02/2016.
 */
public class AddWithdrawalsController implements Initializable {

    @FXML private TableView<WithdrawalsView> tableWithdrawals;
    @FXML private TableColumn<WithdrawalsView, String> cnDetails;
    @FXML private TableColumn<WithdrawalsView, String> cnValue;
    @FXML private TableColumn<WithdrawalsView, String> cnDate;
    @FXML private TextField txtDetails;
    @FXML private TextField txtValue;
    @FXML private DatePicker dpDate;
    @FXML private ImageView imageSave;
    @FXML private ImageView imageCancel;

    private ObservableList<WithdrawalsView> withdrawals;

    private String details;
    private double value;
    private String date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageSave.setImage(new Image(GuiMain.class.getResourceAsStream("save_32x32.png")));
        imageCancel.setImage(new Image(GuiMain.class.getResourceAsStream("cancel_32x32.png")));
        dpDate.setValue(LocalDate.now());

        cnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        cnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        cnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        withdrawals = FXCollections.observableArrayList();
        tableWithdrawals.setItems(withdrawals);
    }

    @FXML
    public void btnAddToTableOnAction(){
        if(!validateInputs())
            return;

        withdrawals.add(
                new WithdrawalsView(details, value, date)
        );
    }

    @FXML
    public void btnSaveOnAction(){
        GuiMain.getWithdrawalsControl().addWithdrawals(
                withdrawals.stream().map(
                        with -> new Withdraw(
                                with.getDetails(), with.getValue(),
                                LocalDate.parse(with.getDate()),
                                GuiMain.getAccountControl().getAccountId(LocalDate.parse(with.getDate()))
                        )
                ).collect(Collectors.toList())
        );

        try{
            GuiMain.getWithdrawalsControl().saveChanges();
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            GuiMain.getAddWithdrawalsWindow().close();
        }
    }

    private boolean validateInputs(){
        details = txtDetails.getText();
        value = Double.parseDouble(txtValue.getText());
        date = dpDate.getValue().toString();

        txtDetails.clear();
        txtValue.clear();

        return true;
    }
}
