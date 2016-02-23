package data;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public class DataSource {
    protected static String address = "http://127.0.0.1/webservice/";

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        DataSource.address = address;
    }
}
