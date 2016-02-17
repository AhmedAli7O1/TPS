package datalayer;

import core.DataViewStyle;
import core.Income;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.idata.IWithdrawalsData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import core.Withdraw;

/**
 * Created by Ahmed ali on 09/02/2016.
 */
public class WithdrawalsData implements IWithdrawalsData {
    @Override
    public List<Withdraw> getWithdrawals(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        // put params into a json object to send
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("date", date);
        jsonToSend.put("style", style);

        WebService webService = new WebService();
        JSONObject obj = webService.getJson("withdrawals", "getWithdrawals", jsonToSend);
        List<Withdraw> withdrawals = new ArrayList<>();
        try {
            JSONArray jsonWithdrawalsArray = obj.getJSONArray("Withdrawals");
            for(int i = 0; i < jsonWithdrawalsArray.length(); i++){
                withdrawals.add(new Withdraw(
                        jsonWithdrawalsArray.getJSONObject(i).getInt("ID"),
                        jsonWithdrawalsArray.getJSONObject(i).getString("Details"),
                        jsonWithdrawalsArray.getJSONObject(i).getDouble("Value"),
                        LocalDate.parse(jsonWithdrawalsArray.getJSONObject(i).getString("Date"))
                ));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return withdrawals;
    }

    @Override
    public boolean addNewWithdrawals(List<Withdraw> withdrawals)throws WSConnException, NoDataException {
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("Withdrawals", withdrawals);

        WebService webService = new WebService();
        JSONObject jsonObj = webService.getJson("withdrawals", "addWithdrawals", jsonToSend);
        if(jsonObj.getBoolean("result")){
            return true;
        }
        else return false;
    }
}
