package datalayer;

import core.Account;
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
    public List<Account> getAccounts() throws WSConnException, NoDataException {
        WebService webService = new WebService();
        JSONObject jsonResult = webService.getJson("accounts", "getAccounts");
        List<Account> accounts = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonResult.getJSONArray("Accounts");
            for (int i = 0; i < jsonArray.length(); i++) {
                accounts.add(
                        new Account(
                                jsonArray.getJSONObject(i).getInt("ID"),
                                jsonArray.getJSONObject(i).getDouble("SALES"),
                                jsonArray.getJSONObject(i).getDouble("DEBTS"),
                                jsonArray.getJSONObject(i).getDouble("INCOMES"),
                                jsonArray.getJSONObject(i).getDouble("OUTGOINGS"),
                                jsonArray.getJSONObject(i).getDouble("WITHDRAWALS"),
                                jsonArray.getJSONObject(i).getDouble("PURCHASES"),
                                jsonArray.getJSONObject(i).getDouble("BALANCE"),
                                jsonArray.getJSONObject(i).getDouble("PROFITS"),
                                LocalDate.parse(jsonArray.getJSONObject(i).getString("DATE"))
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
    public boolean addAccount(double oldBalance, LocalDate date) throws WSConnException, NoDataException {
        WebService webService = new WebService();
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("oldBalance", oldBalance);
        jsonToSend.put("date", date.toString());

        JSONObject jsonResult = webService.getJson("accounts.php", "addAccount", jsonToSend);
        if(jsonResult.getBoolean("result"))
            return true;
        else return false;
    }
}
