package datalayer;

import core.DataViewStyle;
import core.Purchase;
import core.Withdraw;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.idata.IPurchasesData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed ali on 10/02/2016.
 */
public class PurchasesData implements IPurchasesData {
    @Override
    public List<Purchase> getPurchases(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        // put params into a json object to send
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("date", date);
        jsonToSend.put("style", style);

        WebService webService = new WebService();
        JSONObject obj = webService.getJson("purchases", "getPurchases", jsonToSend);
        List<Purchase> purchases = new ArrayList<>();
        try {
            JSONArray jsonPurchasesArray = obj.getJSONArray("Purchases");
            for(int i = 0; i < jsonPurchasesArray.length(); i++){
                purchases.add(new Purchase(
                        jsonPurchasesArray.getJSONObject(i).getInt("ID"),
                        jsonPurchasesArray.getJSONObject(i).getString("ITEM"),
                        jsonPurchasesArray.getJSONObject(i).getInt("AMOUNT"),
                        jsonPurchasesArray.getJSONObject(i).getDouble("PURCHASE_PRICE"),
                        jsonPurchasesArray.getJSONObject(i).getString("SELLER"),
                        LocalDate.parse(jsonPurchasesArray.getJSONObject(i).getString("DATE")),
                        jsonPurchasesArray.getJSONObject(i).getInt("ACCOUNT_ID")
                ));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return purchases;
    }

    @Override
    public boolean addNewPurchases(List<Purchase> purchases) throws WSConnException, NoDataException {
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("Purchases", purchases);
        WebService webService = new WebService();
        JSONObject jsonObj = webService.getJson("purchases", "addPurchase", jsonToSend);
        if(jsonObj.getBoolean("result")){
            return true;
        }else return false;
    }
}
