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
    List<Purchase> newPurchases;

    public PurchasesControl(){
        purchases = new ArrayList<>();
        newPurchases = new ArrayList<>();
    }

    @Override
    public List<Purchase> getPurchases(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        purchases.clear();
        purchases.addAll(purchasesData.getPurchases(date, style));
        return this.purchases;
    }

    @Override
    public void addPurchases(List<Purchase> newPurchases){
        this.newPurchases.addAll(newPurchases);
    }

    @Override
    public boolean saveChanges()throws WSConnException, NoDataException{
        if(newPurchases.size() < 1)
            return true;

        if(purchasesData.addNewPurchases(newPurchases)){
            newPurchases.clear();
            return true;
        }
        else return false;
    }
}
