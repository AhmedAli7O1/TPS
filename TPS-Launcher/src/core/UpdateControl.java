package core;

import core.exceptions.NoDataException;
import core.exceptions.WSConnException;
import data.Updates;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public class UpdateControl {
    private int VER_NUM;
    private Updates updatesData = new Updates();
    private Update lastUpdate;

    public UpdateControl(int verNum){
        this.VER_NUM = verNum;
    }

    private boolean checkForUpdates()throws WSConnException{
        try {
            lastUpdate = updatesData.getLastUpdate();
            if (lastUpdate.getVer() > VER_NUM) {
                //update is needed
                return true;
            } else return false;
        }catch (NoDataException ex){
            return false;
        }
    }

    public DownloadUpdate updateToLastVer(){
        if(lastUpdate != null)
            return new DownloadUpdate(lastUpdate.getLink(), lastUpdate.getSize());
        else {
            System.err.println("no updates were found!");
            return null;
        }
    }
}
