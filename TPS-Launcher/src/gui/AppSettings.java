package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by Ahmed Ali on 24/02/2016.
 */
public class AppSettings {
    private int verNum;
    private int splashTimeout;

    private String settingPath;
    private Properties prop;

    public AppSettings(String settingsPath){
        this.settingPath = settingsPath;

        loadSettings();
    }

    private void loadSettings(){
        prop = new Properties();
        try{
            FileInputStream inputStream = new FileInputStream(this.settingPath);
            prop.load(inputStream);
            inputStream.close();
        }
        catch (FileNotFoundException ex){
            //settings file not found
            //just set the init values
            prop.setProperty("ver", "0");
            prop.setProperty("splash-timeout", "5000");
        }catch (Exception ex){ ex.printStackTrace(); }
    }

    public void save(){
        try {
            FileOutputStream outputStream = new FileOutputStream(settingPath);
            prop.store(outputStream, null);
            outputStream.close();
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }

    public int getVerNum() {
        verNum = Integer.parseInt(prop.getProperty("ver"));
        return verNum;
    }

    public void setVerNum(int verNum) {
        this.verNum = verNum;
        prop.setProperty("ver", String.valueOf(this.verNum));
    }

    public int getSplashTimeout() {
        splashTimeout = Integer.parseInt(prop.getProperty("splash-timeout"));
        return splashTimeout;
    }

    public void setSplashTimeout(int splashTimeout) {
        this.splashTimeout = splashTimeout;
        prop.setProperty("splash-timeout", String.valueOf(this.splashTimeout));
    }
}
