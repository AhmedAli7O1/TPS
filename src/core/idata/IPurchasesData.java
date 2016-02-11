package core.idata;

import core.DataViewStyle;
import core.Purchase;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public interface IPurchasesData {
    List<Purchase> getPurchases(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException;
}
