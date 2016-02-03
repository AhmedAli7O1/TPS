package datalayer;

public class Settings {
    private static String webServiceAddress = "http://79.170.40.36/ahmedali701.com/tps-webservice/";

    public static String getWebServiceAddress() {
        return webServiceAddress;
    }

    public static void setWebServiceAddress(String webServiceAddress) {
        Settings.webServiceAddress = webServiceAddress;
    }
}
