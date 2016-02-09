package datalayer;

import core.DataViewStyle;
import core.Income;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.idata.IIncomesData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomesData implements IIncomesData {
    @Override
    public List<Income> getIncomes(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException {
        // put params into a json object to send
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("date", date);
        jsonToSend.put("style", style);

        WebService webService = new WebService();
        JSONObject obj = webService.getJson("incomes", "getIncomes", jsonToSend);
        List<Income> incomes = new ArrayList<>();
        try {
            JSONArray jsonIncomesArray = obj.getJSONArray("Incomes");
            for(int i = 0; i < jsonIncomesArray.length(); i++){
                incomes.add(new Income(
                        jsonIncomesArray.getJSONObject(i).getInt("ID"),
                        jsonIncomesArray.getJSONObject(i).getString("Details"),
                        jsonIncomesArray.getJSONObject(i).getDouble("Value"),
                        LocalDate.parse(jsonIncomesArray.getJSONObject(i).getString("Date"))
                ));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return incomes;
    }
}
