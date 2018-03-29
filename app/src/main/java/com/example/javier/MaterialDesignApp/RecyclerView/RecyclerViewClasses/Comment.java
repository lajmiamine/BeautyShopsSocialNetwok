package com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses;

/**
 * Created by mal21 on 05/05/2016.
 */
public class Comment {
    int cmnt_id;
    String cmnt_str;
    int cmnt_user;
    int cmnt_store;
    int cmnt_hour;
    int cmnt_min;

    public int getCmnt_id() {
        return cmnt_id;
    }

    public void setCmnt_id(int cmnt_id) {
        this.cmnt_id = cmnt_id;
    }

    public String getCmnt_str() {
        return cmnt_str;
    }

    public void setCmnt_str(String cmnt_str) {
        this.cmnt_str = cmnt_str;
    }

    public int getCmnt_user() {
        return cmnt_user;
    }

    public void setCmnt_user(int cmnt_user) {
        this.cmnt_user = cmnt_user;
    }

    public int getCmnt_store() {
        return cmnt_store;
    }

    public void setCmnt_store(int cmnt_store) {
        this.cmnt_store = cmnt_store;
    }

    public int getCmnt_hour() {
        return cmnt_hour;
    }

    public void setCmnt_hour(int cmnt_hour) {
        this.cmnt_hour = cmnt_hour;
    }

    public int getCmnt_min() {
        return cmnt_min;
    }

    public void setCmnt_min(int cmnt_min) {
        this.cmnt_min = cmnt_min;
    }

    public Comment(int cmnt_id, String cmnt_str, int cmnt_user, int cmnt_store, int cmnt_hour, int cmnt_min) {

        this.cmnt_id = cmnt_id;
        this.cmnt_str = cmnt_str;
        this.cmnt_user = cmnt_user;
        this.cmnt_store = cmnt_store;
        this.cmnt_hour = cmnt_hour;
        this.cmnt_min = cmnt_min;
    }
}
