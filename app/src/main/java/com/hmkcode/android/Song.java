package com.hmkcode.android;

public class Song {

    private int id;
    private String title;
    private String band;

    public Song(){}

    public Song(String title, String band) {
        super();
        this.title = title;
        this.band = band;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }
    //getters & setters

    @Override
    public String toString() {
        return "Band [id=" + id + ", title=" + title + ", band=" + band
                + "]";
    }
}