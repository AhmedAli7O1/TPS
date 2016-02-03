package datalayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebService {

    public static String getJson(String className, String method){
        String strUrl = String.format("%s%s.php?method=%s", Settings.getWebServiceAddress(), className, method);
        return requestData(strUrl);
    }

    public static String getJson(String className, String method, String...parm){
        String strUrl = String.format("%s%s.php?method=%s", Settings.getWebServiceAddress(), className, method);

        if(parm.length > 0) {
            String s = "";
            for (String p : parm) {
                s += "&" + p;
            }
            strUrl += s;
        }
        return requestData(strUrl);
    }

    private static String requestData(String strUrl){
        String json = new String();
        try{
            URL url = new URL(strUrl);

            // read from url
            try{
                Scanner scan = new Scanner(url.openStream());

                while(scan.hasNext()){
                    json += scan.nextLine();
                }
                scan.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        catch(MalformedURLException ex){
            ex.printStackTrace();
        }
        return json;
    }
}
