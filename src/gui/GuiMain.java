package gui;

import core.*;
import core.igui.*;
import gui.controllers.StatisticsMainController;
import gui.controllers.TpsWindowController;
import gui.windows.*;
import javafx.stage.Stage;

/**
 * Created by Ahmed ali on 13/02/2016.
 */
public abstract class GuiMain {
    private static AppSettings appSettings;
    private static Stage loginWindow;
    private static TpsWindow tpsWindow;
    private static AddSalesWindow addSalesWindow;
    private static AddOutgoingsWindow addOutgoingsWindow;
    private static AddIncomesWindow addIncomesWindow;
    private static AddPurchasesWindow addPurchasesWindow;
    private static AddWithdrawalsWindow addWithdrawalsWindow;
    private static TpsWindowController tpsWindowController;
    private static StatisticsMainController statisticsMainController;
    private static IUserControl userControl;
    private static ISalesControl salesControl;
    private static IOutgoingsControl outgoingsControl;
    private static IIncomesControl incomesControl;
    private static IWithdrawalsControl withdrawalsControl;
    private static IPurchasesControl purchasesControl;
    private static IAccountControl accountControl;

    public static TpsWindow getTpsWindow() {
        if(tpsWindow == null)
            tpsWindow = new TpsWindow();

        return tpsWindow;
    }

    public static void setTpsWindow(TpsWindow tpsWindow) {
        GuiMain.tpsWindow = tpsWindow;
    }

    public static AddSalesWindow getAddSalesWindow() {
        if(addSalesWindow == null)
            addSalesWindow = new AddSalesWindow();

        return addSalesWindow;
    }

    public static void setAddSalesWindow(AddSalesWindow addSalesWindow) {
        GuiMain.addSalesWindow = addSalesWindow;
    }

    public static AddOutgoingsWindow getAddOutgoingsWindow() {
        if(addOutgoingsWindow == null)
            addOutgoingsWindow = new AddOutgoingsWindow();

        return addOutgoingsWindow;
    }

    public static void setAddOutgoingsWindow(AddOutgoingsWindow addOutgoingsWindow) {
        GuiMain.addOutgoingsWindow = addOutgoingsWindow;
    }

    public static TpsWindowController getTpsWindowController() {
        return tpsWindowController;
    }

    public static void setTpsWindowController(TpsWindowController tpsWindowCR) {
       tpsWindowController = tpsWindowCR;
    }

    public static IUserControl getUserControl() {
        if(userControl == null)
            userControl = new UserControl();

        return userControl;
    }

    public static void setUserControl(IUserControl userControl) {
        GuiMain.userControl = userControl;
    }

    public static ISalesControl getSalesControl() {
        if(salesControl == null)
            salesControl = new SalesControl();

        return salesControl;
    }

    public static void setSalesControl(ISalesControl salesControl) {
        GuiMain.salesControl = salesControl;
    }

    public static IOutgoingsControl getOutgoingsControl() {
        if(outgoingsControl == null)
            outgoingsControl = new OutgoingsControl();

        return outgoingsControl;
    }

    public static void setOutgoingsControl(IOutgoingsControl outgoingsControl) {
        GuiMain.outgoingsControl = outgoingsControl;
    }

    public static IIncomesControl getIncomesControl() {
        if(incomesControl == null)
            incomesControl = new IncomesControl();

        return incomesControl;
    }

    public static void setIncomesControl(IIncomesControl incomesControl) {
        GuiMain.incomesControl = incomesControl;
    }

    public static IWithdrawalsControl getWithdrawalsControl() {
        if(withdrawalsControl == null)
            withdrawalsControl = new WithdrawalsControl();

        return withdrawalsControl;
    }

    public static void setWithdrawalsControl(IWithdrawalsControl withdrawalsControl) {
        GuiMain.withdrawalsControl = withdrawalsControl;
    }

    public static IPurchasesControl getPurchasesControl() {
        if(purchasesControl == null)
            purchasesControl = new PurchasesControl();

        return purchasesControl;
    }

    public static void setPurchasesControl(IPurchasesControl purchasesControl) {
        GuiMain.purchasesControl = purchasesControl;
    }

    public static Stage getLoginWindow() {
        return loginWindow;
    }

    public static void setLoginWindow(Stage loginWindow) {
        GuiMain.loginWindow = loginWindow;
    }

    public static AddIncomesWindow getAddIncomesWindow() {
        if(addIncomesWindow == null)
            addIncomesWindow = new AddIncomesWindow();

        return addIncomesWindow;
    }

    public static void setAddIncomesWindow(AddIncomesWindow addIncomesWindow) {
        GuiMain.addIncomesWindow = addIncomesWindow;
    }

    public static IAccountControl getAccountControl() {
        if(accountControl == null)
            accountControl = new AccountsControl();

        return accountControl;
    }

    public static void setAccountControl(IAccountControl accountControl) {
        GuiMain.accountControl = accountControl;
    }

    public static StatisticsMainController getStatisticsMainController() {
        return statisticsMainController;
    }

    public static void setStatisticsMainController(StatisticsMainController statisticsMainController) {
        GuiMain.statisticsMainController = statisticsMainController;
    }

    public static AddPurchasesWindow getAddPurchasesWindow() {
        if(addPurchasesWindow == null)
            addPurchasesWindow = new AddPurchasesWindow();

        return addPurchasesWindow;
    }

    public static void setAddPurchasesWindow(AddPurchasesWindow addPurchasesWindow) {
        GuiMain.addPurchasesWindow = addPurchasesWindow;
    }

    public static AddWithdrawalsWindow getAddWithdrawalsWindow() {
        if(addWithdrawalsWindow == null)
            addWithdrawalsWindow = new AddWithdrawalsWindow();

        return addWithdrawalsWindow;
    }

    public static void setAddWithdrawalsWindow(AddWithdrawalsWindow addWithdrawalsWindow) {
        GuiMain.addWithdrawalsWindow = addWithdrawalsWindow;
    }

    public static AppSettings getAppSettings() {
        return appSettings;
    }

    public static void setAppSettings(AppSettings appSettings) {
        GuiMain.appSettings = appSettings;
    }
}
