package com.example.Entity;

/**
 * Created by luzhiyuan on 16/6/15.
 */
public class Song {
    private int id;
    private String song_name;
    private float song_size;
    private String lrc_name;
    private float lrc_size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getLrc_name() {
        return lrc_name;
    }

    public void setLrc_name(String lrc_name) {
        this.lrc_name = lrc_name;
    }

    public float getSong_size() {
        return song_size;
    }

    public void setSong_size(float song_size) {
        this.song_size = song_size;
    }

    public float getLrc_size() {
        return lrc_size;
    }

    public void setLrc_size(float lrc_size) {
        this.lrc_size = lrc_size;
    }
}
