package com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses;


public class Checkin {

    public Stores stores;
    public int hour;
    public int min;

    public Checkin(Stores stores, int hour, int min) {
        this.stores = stores;
        this.hour = hour;
        this.min = min;
    }

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
