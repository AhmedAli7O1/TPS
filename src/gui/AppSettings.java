package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by Ahmed Ali on 25/02/2016.
 */
public class AppSettings {

    private String settingsPath;
    private Properties prop;

    public AppSettings(String settingsPath){
        this.settingsPath = settingsPath;
    }

    public boolean loadSettings(){
        prop = new Properties();

        try{
            FileInputStream inputStream = new FileInputStream(this.settingsPath);
            prop.load(inputStream);
            inputStream.close();
            return true;
        }
        catch (FileNotFoundException ex){
            //settings file not found
            //just set the init values
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public void save(){
        try {
            FileOutputStream outputStream = new FileOutputStream(settingsPath);
            prop.store(outputStream, null);
            outputStream.close();
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }

    public int getVerNum() {
        return Integer.parseInt(prop.getProperty("ver"));
    }

    public void setVerNum(int verNum) {
        prop.setProperty("ver", String.valueOf(verNum));
    }

    public int getSplashTimeout() {
        return Integer.parseInt(prop.getProperty("splash-timeout"));
    }

    public void setSplashTimeout(int splashTimeout) {
        prop.setProperty("splash-timeout", String.valueOf(splashTimeout));
    }

    public String getDataSource() {
        return prop.getProperty("data-source");
    }

    public void setDataSource(String dataSource) {
        prop.setProperty("data-source", dataSource);
    }

    public String getAppPath(){
        return prop.getProperty("app-path");
    }

    public void setAppPath(String appPath){
        prop.setProperty("app-path", appPath);
    }

    public String getSettingsPath(){
        return settingsPath;
    }
}
