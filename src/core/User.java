package core;

import org.joda.time.*;

public class User extends CoreMain{
    /**
     * ###########################
     * ##### local variables #####
     * ###########################
     */
    private int id;
    private String name;
    private String password;
    private Authorization auth;
    private String title;
    private DateTime lastLogged;
    private DateTime lastEdit;

    public User(int id, String name, String password, Authorization auth,
                String title, DateTime lastLogged, DateTime lastEdit){
        this.id = id;
        this.name = name;
        this.password = password;
        this.auth = auth;
        this.title = title;
        this.lastLogged = lastLogged;
        this.lastEdit = lastEdit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
