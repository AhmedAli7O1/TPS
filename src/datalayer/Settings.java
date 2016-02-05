package datalayer;

public class Settings {
    private static String webServiceAddress = "http://127.0.0.1/tps-webservice/";

    public static String getWebServiceAddress() {
        return webServiceAddress;
    }

    public static void setWebServiceAddress(String webServiceAddress) {
        Settings.webServiceAddress = webServiceAddress;
    }
}
