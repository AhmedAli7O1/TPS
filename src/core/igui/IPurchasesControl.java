package core.igui;

import core.DataViewStyle;
import core.Purchase;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public interface IPurchasesControl {
    List<Purchase> getPurchases(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
    void addPurchases(List<Purchase> newPurchases);
    boolean saveChanges()throws WSConnException, NoDataException;
}
