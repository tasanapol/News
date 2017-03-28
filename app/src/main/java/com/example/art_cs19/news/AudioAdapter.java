package com.example.art_cs19.news;

/**
 * Created by Art_cs19 on 3/24/2017 AD.
 */

public class AudioAdapter {
    private String title;
    private String uploader;
    private String image;
    private String date;
    private String time;
    private String id;
    private String audio;
    private String narrator;

    public AudioAdapter() {
    }



    public AudioAdapter(String title, String uploader, String image, String date, String time, String id, String audio, String narrator) {
        this.title = title;
        this.uploader = uploader;
        this.image = image;
        this.date = date;
        this.time = time;
        this.id = id;
        this.audio = audio;
        this.narrator = narrator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getNarrator() {
        return narrator;
    }

    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }


}
