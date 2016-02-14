package gui.controllers;

import core.DataViewStyle;
import core.igui.*;
import gui.GuiMain;
import gui.windows.AddOutgoingsWindow;
import gui.windows.AddSalesWindow;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TpsWindowController implements Initializable {

    @FXML private VBox statisticsMain;
    @FXML private VBox purchasesMain;
    @FXML private VBox outgoingsMain;
    @FXML private VBox incomesMain;
    @FXML private VBox salesMain;                                    //Sales Window
    @FXML private VBox withdrawalsMain;
    @FXML private StatisticsMainController statisticsMainController;
    @FXML private WithdrawalsMainController withdrawalsMainController;
    @FXML private PurchasesMainController purchasesMainController;
    @FXML private OutgoingsMainController outgoingsMainController;
    @FXML private IncomesMainController incomesMainController;
    @FXML private SalesMainController salesMainController;           //Sales Window Controller
    @FXML private Label lblUserName;                                 //label shows current user name
    @FXML private TextField txtUserName;
    @FXML private Pane paneLoading;                                  //Loading Pane
    @FXML private ImageView imageUserIcon;
    @FXML private ImageView imageOutgoings;
    @FXML private ImageView imageSales;
    @FXML private ImageView imageIncomes;
    @FXML private ImageView imagePurchases;
    @FXML private ImageView imageWithdrawals;
    @FXML private ImageView imageStatistics;
    @FXML private ImageView imageCalendar;
    @FXML private ImageView imageCalc;
    @FXML private Accordion rightAccordion;
    @FXML private TitledPane btnStatistics;
    private List<VBox> panes;                                        //List of panes "Sales, Outgoings, Withdrawals...etc"
    private VBox currPane;                                           //Current Pane
    private ObservableList<String> dataViewStyleList;                //how sales are displayed daily, monthly...etc


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GuiMain.setTpsWindowController(this);

        // set some icons
        imageUserIcon.setImage(new Image(GuiMain.class.getResourceAsStream("user_32x32.png")));
        imageSales.setImage(new Image(GuiMain.class.getResourceAsStream("sales_16x16.png")));
        imageOutgoings.setImage(new Image(GuiMain.class.getResourceAsStream("out_16x16.png")));
        imageIncomes.setImage(new Image(GuiMain.class.getResourceAsStream("in_16x16.png")));
        imagePurchases.setImage(new Image(GuiMain.class.getResourceAsStream("purchases_16x16.png")));
        imageWithdrawals.setImage(new Image(GuiMain.class.getResourceAsStream("withdrawals_16x16.png")));
        imageStatistics.setImage(new Image(GuiMain.class.getResourceAsStream("statistics_16x16.png")));
        imageCalendar.setImage(new Image(GuiMain.class.getResourceAsStream("calendar_32x32.png")));
        imageCalc.setImage(new Image(GuiMain.class.getResourceAsStream("calc_32x32.png")));

        // create list contains all panes to control them
        panes = new ArrayList<>();
        panes.add(statisticsMain);
        panes.add(salesMain);
        panes.add(outgoingsMain);
        panes.add(purchasesMain);
        panes.add(incomesMain);
        panes.add(withdrawalsMain);

        // by default all panes not visible
        salesMain.setVisible(false);
        outgoingsMain.setVisible(false);
        purchasesMain.setVisible(false);
        incomesMain.setVisible(false);
        withdrawalsMain.setVisible(false);
        statisticsMain.setVisible(true);

        // init View Style ComboBox
        dataViewStyleList = FXCollections.observableArrayList();
        dataViewStyleList.add("يومى");
        dataViewStyleList.add("شهرى");
        dataViewStyleList.add("سنوى");

        // get user data
        //lblUserName.setText(userControl.getCurrentUser().getName());
        txtUserName.setText(GuiMain.getUserControl().getCurrentUser().getName());

        rightAccordion.setExpandedPane(btnStatistics);  //set Statistics TitledPane to expanded by default

        // initialize sub controllers
        incomesMainController.init();
        outgoingsMainController.init();
        purchasesMainController.init();
        salesMainController.init();
        withdrawalsMainController.init();
    }

    //sales TitledPane onMouseClicked Event
    public void salesOnClicked(){ showPane(salesMain); }
    //outgoings TitledPane onMouseClicked Event
    public void outgoingsOnClicked() { showPane(outgoingsMain); }
    //purchases TitledPane onMouseClicked Event
    public void purchasesOnClicked() { showPane(purchasesMain); }
    //Incomes TitledPane onMouseClicked Event
    public void btnIncomesOnClicked() { showPane(incomesMain); }
    //Withdrawals TitledPane onMouseClicked Event
    public void btnWithdrawalsOnClicked(){ showPane(withdrawalsMain); }
    //Statistics TitledPane onMouseClicked Event
    public void statisticsOnClicked(){ showPane(statisticsMain); }

    // show and hide panes "Windows"
    public void showPane(VBox pane){
        panes.forEach(p -> {
            if(p == pane) {
                p.setVisible(true);
                currPane = pane;
            }
            else
                p.setVisible(false);
        });
    }

    public void showLoading(){
        paneLoading.setVisible(true);
        currPane.setDisable(true);
    }

    public void hideLoading(){
        currPane.setDisable(false);
        paneLoading.setVisible(false);
    }


    /**
     * OnAction Event for button AddSales
     * when user click this button
     * a new window will show to add new order
     */
    @FXML
    public void btnAddSalesOnClick(){
        GuiMain.setAddSalesWindow(new AddSalesWindow());
        GuiMain.getAddSalesWindow().showAndWait();     //show new window to Add new Order
        salesMainController.btnSalesViewOnAction();    //refresh sales list
    }

    @FXML
    public void btnAddOutgoingsOnClick(){
        GuiMain.setAddOutgoingsWindow(new AddOutgoingsWindow());
        GuiMain.getAddOutgoingsWindow().showAndWait();      //show AddOutgoingsWindow and wait till user close it
        outgoingsMainController.btnOutgoingsViewOnAction(); //refresh Outgoings list
    }

    //read ComboBox value and return it as SalesViewStyle
    public DataViewStyle getDataViewStyle(ComboBox<String> cbox){
        if(cbox.getSelectionModel().isSelected(1))
            return DataViewStyle.MONTH;
        else if(cbox.getSelectionModel().isSelected(0))
            return DataViewStyle.DAY;
        else if(cbox.getSelectionModel().isSelected(2))
            return DataViewStyle.YEAR;
        else
            return null;
    }

    public ObservableList<String> getDataViewStyleList() {
        return dataViewStyleList;
    }

    public void test(){
        System.out.println("workssssssssssssssssssssssssssss");
    }
}
