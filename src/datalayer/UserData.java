package datalayer;

import core.Authorization;
import core.idata.IUserData;
import core.User;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import core.exceptions.LoginException;

public class UserData implements IUserData{

    @Override
    public User login(String uName, String uPass) throws LoginException{
        try {
            JSONObject jsonObj = new JSONObject(WebService.getJson("users", "login", "uName=" + uName, "uPass=" + uPass));
            return new User(jsonObj.getString("Name"),
                    Authorization.valueOf(jsonObj.getString("Auth")),
                    jsonObj.getString("Title"),
                    DateTime.parse(jsonObj.getString("LastLogged")),
                    DateTime.parse(jsonObj.getString("LastEdit")),
                    jsonObj.getInt("SecKey"));
        }
        catch (JSONException ex){
            throw new LoginException(LoginException.ExType.INVALID_ID_PASS);
        }
    }
}
