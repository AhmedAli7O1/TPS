package datalayer;

import core.Account;
import core.DataViewStyle;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.idata.IAccountData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Ali on 16/02/2016.
 */
public class AccountsData implements IAccountData {

    @Override
    public List<Account> getAccounts(int year) throws WSConnException, NoDataException {
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("year", year);

        WebService webService = new WebService();
        JSONObject jsonResult = webService.getJson("accounts", "getAccounts", jsonToSend);
        List<Account> accounts = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonResult.getJSONArray("Accounts");
            for (int i = 0; i < jsonArray.length(); i++) {
                accounts.add(
                        new Account(
                                jsonArray.getJSONObject(i).getInt("ID"),
                                jsonArray.getJSONObject(i).getDouble("TotalSales"),
                                jsonArray.getJSONObject(i).getDouble("TotalIncomes"),
                                jsonArray.getJSONObject(i).getDouble("TotalOutgoings"),
                                jsonArray.getJSONObject(i).getDouble("TotalWithdrawals"),
                                jsonArray.getJSONObject(i).getDouble("TotalPurchases"),
                                LocalDate.parse(jsonArray.getJSONObject(i).getString("Date"))
                        )
                );
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<LocalDate> getDates(){

        List<LocalDate> dates = new ArrayList<>();

        try {
            WebService webService = new WebService();
            JSONObject jsonResult = webService.getJson("accounts", "getDates");

            if (jsonResult.getInt("size") > 0) {
                JSONArray datesArray = jsonResult.getJSONArray("Dates");
                for (int i = 0; i < datesArray.length(); i++) {
                    dates.add(LocalDate.parse(datesArray.getJSONObject(i).getString("Date")));
                }
            }
        }
        catch (Exception ex){ ex.printStackTrace(); }

        return dates;
    }
}
