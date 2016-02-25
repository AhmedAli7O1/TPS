package datalayer;

import core.UserType;
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

            return new User(jsonResponse.getString("NAME"),
                    UserType.valueOf(jsonResponse.getString("AUTH")),
                    jsonResponse.getString("TITLE"),
                    DateTime.parse(jsonResponse.getString("LAST_LOGGED")),
                    DateTime.parse(jsonResponse.getString("LAST_EDIT")),
                    jsonResponse.getInt("SEC_KEY"));
        }
        catch (NoDataException ex){
            throw new LoginException(LoginException.ExType.INVALID_ID_PASS);
        }
    }
}
