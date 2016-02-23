package data;

import core.Update;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import org.json.JSONObject;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public class Updates {
    public Update getLastUpdate() throws WSConnException, NoDataException {
        WebService webService = new WebService();

        JSONObject jsonResult = webService.getJson("updates", "getLastUpdate");
        JSONObject obj = jsonResult.getJSONObject("LastUpdate");
        Update lastUpdate = new Update(
                obj.getInt("ID"),
                obj.getInt("VER"),
                obj.getString("LINK"),
                obj.getInt("SIZE"),
                obj.getString("HASH")
        );
        return lastUpdate;
    }
}
