package datalayer;

public class DataSource {
    protected static String address = "http://127.0.0.1/webservice/";

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        DataSource.address = address;
    }
}
