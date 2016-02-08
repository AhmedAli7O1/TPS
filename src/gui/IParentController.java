package gui;

import core.DataViewStyle;
import core.igui.IOutgoingsControl;
import core.igui.ISalesControl;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public interface IParentController {
    ObservableList<String> getDataViewStyleList();
    DataViewStyle getDataViewStyle(ComboBox<String> cbox);
    ISalesControl getSalesControl();
    IOutgoingsControl getOutgoingsControl();
    void showPane(VBox pane);
    void showLoading();
    void hideLoading();
}
