package gui.controllers;

import gui.GuiMain;
import gui.Main;
import gui.OutgoingsView;
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
import core.Outgoing;
import java.time.LocalDate;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Ahmed ali on 11/02/2016.
 */
public class AddOutgoingsController implements Initializable {
    @FXML private ImageView imageSave;
    @FXML private ImageView imageCancel;
    @FXML private ImageView imageInfo;
    @FXML private TextField txtDetails;
    @FXML private DatePicker dpDate;
    @FXML private TextField txtValue;
    @FXML private TableView<OutgoingsView> tableOutgoings;
    @FXML private TableColumn<OutgoingsView, String> columnDetails;
    @FXML private TableColumn<OutgoingsView, Double> columnValue;
    @FXML private TableColumn<OutgoingsView, String> columnDate;

    private ObservableList<OutgoingsView> outgoingsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageSave.setImage(new Image(GuiMain.class.getResourceAsStream("save_32x32.png")));
        imageCancel.setImage(new Image(GuiMain.class.getResourceAsStream("cancel_32x32.png")));
        imageInfo.setImage(new Image(GuiMain.class.getResourceAsStream("money2_64x64.png")));

        columnDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        outgoingsList = FXCollections.observableArrayList();

        tableOutgoings.setItems(outgoingsList);
    }

    @FXML
    public void btnAddToTableOnAction(){
        String details = txtDetails.getText();
        String date = dpDate.getValue().toString();
        double value = Double.parseDouble(txtValue.getText());

        outgoingsList.add(new OutgoingsView(details, value, date));
    }

    @FXML
    public void btnSaveOnAction(){
        GuiMain.getOutgoingsControl().addOutgoings(outgoingsList.stream().map(outgoingView -> new Outgoing(
                outgoingView.getDetails(),
                outgoingView.getValue(),
                LocalDate.parse(outgoingView.getDate())
        )).collect(Collectors.toList()));
        GuiMain.getAddOutgoingsWindow().close();

        try {
            GuiMain.getOutgoingsControl().saveChanges();   //save all changes to data source
        }catch (Exception ex){}
    }
}
