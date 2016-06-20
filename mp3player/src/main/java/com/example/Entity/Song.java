package com.example.Entity;

import java.io.Serializable;

/**
 * Created by luzhiyuan on 16/6/15.
 */
public class Song implements Serializable {
    private int id;
    private String song_name;
    private float song_size;
    private String lrc_name;
    private float lrc_size;
    private String song_name_en;
    private String lrc_name_en;

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

    public String getSong_name_en() {
        return song_name_en;
    }

    public void setSong_name_en(String song_name_en) {
        this.song_name_en = song_name_en;
    }

    public String getLrc_name_en() {
        return lrc_name_en;
    }

    public void setLrc_name_en(String lrc_name_en) {
        this.lrc_name_en = lrc_name_en;
    }
}
