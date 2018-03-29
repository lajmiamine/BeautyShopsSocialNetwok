package com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses;

/**
 * Created by mal21 on 04/05/2016.
 */
public class User {

    public int usr_id;
    public String usr_name;
    public int usr_bday_day;
    public int usr_bday_month;
    public int usr_bday_year;
    public String usr_email;
    public String usr_pass;

    public User(int usr_id, String usr_name, int usr_bday_day, int usr_bday_month, int usr_bday_year) {
        this.usr_id = usr_id;
        this.usr_name = usr_name;
        this.usr_bday_day = usr_bday_day;
        this.usr_bday_month = usr_bday_month;
        this.usr_bday_year = usr_bday_year;
    }

    public int getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(int usr_id) {
        this.usr_id = usr_id;
    }

    public String getUsr_name() {
        return usr_name;
    }

    public void setUsr_name(String usr_name) {
        this.usr_name = usr_name;
    }

    public int getUsr_bday_day() {
        return usr_bday_day;
    }

    public void setUsr_bday_day(int usr_bday_day) {
        this.usr_bday_day = usr_bday_day;
    }

    public int getUsr_bday_month() {
        return usr_bday_month;
    }

    public void setUsr_bday_month(int usr_bday_month) {
        this.usr_bday_month = usr_bday_month;
    }

    public int getUsr_bday_year() {
        return usr_bday_year;
    }

    public void setUsr_bday_year(int usr_bday_year) {
        this.usr_bday_year = usr_bday_year;
    }

    public String getUsr_email() {
        return usr_email;
    }

    public void setUsr_email(String usr_email) {
        this.usr_email = usr_email;
    }

    public String getUsr_pass() {
        return usr_pass;
    }

    public void setUsr_pass(String usr_pass) {
        this.usr_pass = usr_pass;
    }
}
