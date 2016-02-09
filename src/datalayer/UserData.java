package datalayer;

import core.Authorization;
import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import core.idata.IUserData;
import core.User;
import org.joda.time.DateTime;
import org.json.JSONObject;
import core.exceptions.LoginException;

public class UserData implements IUserData{

    @Override
    public User login(String uName, String uPass) throws LoginException, WSConnException{
            WebService webService = new WebService();

            JSONObject jsonToSend = new JSONObject();
            jsonToSend.put("uName", uName);
            jsonToSend.put("uPass", uPass);

        try {
            JSONObject jsonResponse = webService.getJson("users", "login", jsonToSend);

            return new User(jsonResponse.getString("Name"),
                    Authorization.valueOf(jsonResponse.getString("Auth")),
                    jsonResponse.getString("Title"),
                    DateTime.parse(jsonResponse.getString("LastLogged")),
                    DateTime.parse(jsonResponse.getString("LastEdit")),
                    jsonResponse.getInt("SecKey"));
        }
        catch (NoDataException ex){
            throw new LoginException(LoginException.ExType.INVALID_ID_PASS);
        }
    }
}
