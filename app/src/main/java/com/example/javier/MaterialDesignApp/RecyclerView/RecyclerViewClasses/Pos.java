package com.example.javier.MaterialDesignApp.RecyclerView.RecyclerViewClasses;

/**
 * Created by mal21 on 04/05/2016.
 */
public class Pos {
    int pos;
    long id;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pos(int pos, long id) {

        this.pos = pos;
        this.id = id;
    }
}
