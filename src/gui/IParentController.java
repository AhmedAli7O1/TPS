package gui;

import core.DataViewStyle;
import core.igui.*;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public interface IParentController {
    ObservableList<String> getDataViewStyleList();
    DataViewStyle getDataViewStyle(ComboBox<String> cbox);
    ISalesControl getSalesControl();
    IOutgoingsControl getOutgoingsControl();
    IIncomesControl getIncomesControl();
    IWithdrawalsControl getWithdrawalsControl();
    IPurchasesControl getPurchasesControl();
    void showPane(VBox pane);
    void showLoading();
    void hideLoading();
}
