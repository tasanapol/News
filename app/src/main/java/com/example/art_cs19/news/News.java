package com.example.art_cs19.news;

/**
 * Created by Art_cs19 on 1/19/2017 AD.
 */

public class News {

    private String title;
    private String description;
    private String image;
    private String date;
    private String time;
    private String id;


    public News() {
    }

    public News(String title, String description, String image , String date, String time, String id) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}
