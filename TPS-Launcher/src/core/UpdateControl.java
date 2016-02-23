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
            if (lastUpdate.getVer() > VER_NUM) {
                //update is needed
                try {
                    return updateToLastVer();
                }catch (Exception ex){
                    ex.printStackTrace();
                    return false;
                }
            } else return false;
        }catch (NoDataException ex){
            return false;
        }
    }

    public boolean updateToLastVer()throws IOException{
        if(lastUpdate != null) {
            DownloadUpdate downloadUpdate = new DownloadUpdate(lastUpdate.getLink(), jarPath);
            downloadUpdate.download();
            return downloadUpdate.checkDownloadedFile(lastUpdate.getHash());
        }
        else {
            System.err.println("no updates were found!");
            return false;
        }
    }


}
