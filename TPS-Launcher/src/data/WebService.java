package data;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService extends DataSource {
    private String urlParameters =  "method=";
    private JSONObject jsonObject;
    private String strUrl;

    public JSONObject getJson(String className, String methodName) throws NoDataException, WSConnException {
        strUrl = address;

        strUrl += className + ".php";
        urlParameters += methodName;

        try {
            return new JSONObject(requestJson());
        }
        catch (JSONException ex){
            throw new NoDataException(ex.getMessage());
        }
        catch (Exception ex){
            throw new WSConnException(ex.getMessage());
        }
    }

    public JSONObject getJson(String className, String methodName, Object obj) throws WSConnException, NoDataException{
        strUrl = address;

        strUrl += className + ".php";
        urlParameters += methodName;

        if(obj instanceof JSONObject)
            jsonObject = (JSONObject) obj;
        else
            this.jsonObject = new JSONObject(obj);

        urlParameters += "&json=" + jsonObject.toString();

        try {
            return new JSONObject(requestJson());
        }
        catch (JSONException ex){
            throw new NoDataException(ex.getMessage());
        }
        catch (Exception ex){
            throw new WSConnException(ex.getMessage());
        }

    }

    private String requestJson()throws Exception{

        URL url = new URL(strUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        connection.setUseCaches (false);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        bw.write(urlParameters);
        bw.flush();

        String response = new String();
        String partRes;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((partRes = reader.readLine()) != null) {
            response += partRes;
        }

        bw.close();
        reader.close();

        System.out.println("############# Debug - File = 'WebService' Line = '88' #############");
        System.out.println("WebService Request : ");
        System.out.println(urlParameters);
        System.out.println("WebService Response : ");
        System.out.println(response);
        System.out.println("### End of Debug Line = '93' ###");

        return response;
    }
}

