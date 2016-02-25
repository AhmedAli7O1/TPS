package core;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public class Update {
    private int id;
    private int ver;
    private String link;
    private int size;
    private String appHash;
    private String settingsHash;

    public Update(int id, int ver, String link, int size, String appHash, String settingsHash) {
        this.id = id;
        this.ver = ver;
        this.link = link;
        this.size = size;
        this.appHash = appHash;
        this.settingsHash = settingsHash;
    }

    public Update(int ver, String link, int size, String appHash, String settingsHash) {
        this.ver = ver;
        this.link = link;
        this.size = size;
        this.appHash = appHash;
        this.settingsHash = settingsHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAppHash() {
        return appHash;
    }

    public void setAppHash(String appHash) {
        this.appHash = appHash;
    }

    public String getSettingsHash() {
        return settingsHash;
    }

    public void setSettingsHash(String settingsHash) {
        this.settingsHash = settingsHash;
    }
}
