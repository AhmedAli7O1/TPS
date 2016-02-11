package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.igui.IPurchasesControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public class PurchasesControl extends CoreMain implements IPurchasesControl{
    List<Purchase> purchases;

    public PurchasesControl(){
        purchases = new ArrayList<>();
    }

    @Override
    public List<Purchase> getPurchases(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        purchases.clear();
        purchases.addAll(purchasesData.getPurchases(date, style));
        return this.purchases;
    }
}
