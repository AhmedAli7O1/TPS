package datalayer;

import core.Authorization;
import core.idata.IUserData;
import core.User;
import org.joda.time.DateTime;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class UserData implements IUserData{

    @Override
    public List<User> getAllUsers() {
        // create new User List to store all users
        List<User> users = new ArrayList<>();

        // get user data from database in json format
        JSONArray jArray = new JSONArray(WebService.getJson("UserData", "getAllUsers"));
        for(int i = 0; i < jArray.length(); i++){
            users.add(new User(jArray.getJSONObject(i).getInt("ID"),
                    jArray.getJSONObject(i).getString("Name"),
                    jArray.getJSONObject(i).getString("Password"),
                    Authorization.valueOf(jArray.getJSONObject(i).getString("Auth")),
                    jArray.getJSONObject(i).getString("Title"),
                    DateTime.parse(jArray.getJSONObject(i).getString("LastLogged")),
                    DateTime.parse(jArray.getJSONObject(i).getString("LastEdit"))));
        }
        return  users;
    }
}
