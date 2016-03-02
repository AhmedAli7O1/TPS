package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import data.Updates;
import gui.GuiMain;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public class UpdateControl {
    private Updates updatesData = new Updates();
    private Update lastUpdate;

    public UpdateControl(){
    }

    public boolean checkForUpdates()throws WSConnException{
        try {
            lastUpdate = updatesData.getLastUpdate();
            if(lastUpdate == null) {
                System.out.println("no updates ");
                return true;         // no updates found..
            }

            if (lastUpdate.getVer() > GuiMain.getAppSettings().getVerNum()) {
                System.out.println("update found");
                //update is needed
                try {
                    return updateToLastVer();
                }catch (Exception ex){
                    ex.printStackTrace();
                    return false;    // problem in updating process
                }
            } else return true;      // the application is at last version
        }catch (NoDataException ex){
            ex.printStackTrace();
            return true;             //no updates
        }
    }

    public boolean updateToLastVer()throws IOException{
        if(lastUpdate != null) {
            System.out.println("start downloading update");

            //download TPS.jar -> app file
            DownloadUpdate downloadApp = new DownloadUpdate(lastUpdate.getLink(), GuiMain.getAppSettings().getAppPath());
            downloadApp.download();  //start download last update
            boolean result = downloadApp.checkDownloadedFile(lastUpdate.getAppHash()); //true if the download file is valid

            if(result) {
                // set version number
                GuiMain.getAppSettings().setVerNum(lastUpdate.getVer());
                GuiMain.getAppSettings().save();

                return true;
            }
            else return false;
        }
        else {
            System.err.println("no updates were found!");
            return true;   //no updates needed
        }
    }
}
