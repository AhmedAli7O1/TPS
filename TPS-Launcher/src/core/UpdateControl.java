package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import data.Updates;

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
    private int VER_NUM;
    private Updates updatesData = new Updates();
    private Update lastUpdate;
    private String jarPath;

    public UpdateControl(String jarPath, int verNum){
        this.VER_NUM = verNum;
        this.jarPath = jarPath;
    }

    public boolean checkForUpdates()throws WSConnException{
        try {
            lastUpdate = updatesData.getLastUpdate();
            if(lastUpdate == null) {
                System.out.println("no updates ");
                return true;         // no updates found..
            }

            if (lastUpdate.getVer() > VER_NUM) {
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
            DownloadUpdate downloadUpdate = new DownloadUpdate(lastUpdate.getLink(), jarPath);
            downloadUpdate.download();  //start download last update
            return downloadUpdate.checkDownloadedFile(lastUpdate.getHash()); //true if the download file is valid
        }
        else {
            System.err.println("no updates were found!");
            return true;   //no updates needed
        }
    }

    public int getLastUpdateVer(){
        if(lastUpdate != null)
            return lastUpdate.getVer();
        else return VER_NUM;
    }

}
