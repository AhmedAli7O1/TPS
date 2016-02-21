package datalayer;

import core.DataViewStyle;
import core.Outgoing;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.idata.IOutgoingsData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OutgoingsData implements IOutgoingsData {
    @Override
    public List<Outgoing> getOutgoings(LocalDate date, DataViewStyle style) throws WSConnException, NoDataException{
        // put params into a json object to send
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("date", date);
        jsonToSend.put("style", style);

        WebService webService = new WebService();
        JSONObject obj = webService.getJson("outgoings", "getOutgoings", jsonToSend);
        List<Outgoing> outgoings = new ArrayList<>();
        try {
            JSONArray jsonOutgoingsArray = obj.getJSONArray("Outgoings");
            for(int i = 0; i < jsonOutgoingsArray.length(); i++){
                outgoings.add(new Outgoing(
                        jsonOutgoingsArray.getJSONObject(i).getInt("ID"),
                        jsonOutgoingsArray.getJSONObject(i).getString("DETAILS"),
                        jsonOutgoingsArray.getJSONObject(i).getDouble("VALUE"),
                        LocalDate.parse(jsonOutgoingsArray.getJSONObject(i).getString("DATE")),
                        jsonOutgoingsArray.getJSONObject(i).getInt("ACCOUNT_ID")
                ));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return outgoings;
    }

    @Override
    public boolean addNewOutgoings(List<Outgoing> outgoings) throws WSConnException, NoDataException {
        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("Outgoings", outgoings);

        WebService webService = new WebService();
        JSONObject jsonResult = webService.getJson("outgoings", "addOutgoings", jsonToSend);
        if(jsonResult.getBoolean("result"))
            return true;
        else return false;
    }
}
