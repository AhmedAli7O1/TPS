package core;

import org.joda.time.*;

public class User extends CoreMain{
    /**
     * ###########################
     * ##### local variables #####
     * ###########################
     */
    private String name;
    private Authorization auth;
    private String title;
    private DateTime lastLogged;
    private DateTime lastEdit;
    private int secKey;

    public User(String name, Authorization auth,
                String title, DateTime lastLogged, DateTime lastEdit, int secKey){
        this.name = name;
        this.auth = auth;
        this.title = title;
        this.lastLogged = lastLogged;
        this.lastEdit = lastEdit;
        this.secKey = secKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Authorization getAuth() {
        return auth;
    }

    public void setAuth(Authorization auth) {
        this.auth = auth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged(DateTime lastLogged) {
        this.lastLogged = lastLogged;
    }

    public DateTime getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(DateTime lastEdit) {
        this.lastEdit = lastEdit;
    }

    public int getSecKey() {
        return secKey;
    }

    public void setSecKey(int secKey) {
        this.secKey = secKey;
    }
}
