package com.example.art_cs19.news;

import android.widget.Button;

/**
 * Created by USER on 17/4/2560.
 */

public class ContactAdapter {
    private int imgId;
    private String name;
    private String phone;
    private Button btn1;

    public ContactAdapter(int imgId, String name, String phone){
        this.imgId = imgId;
        this.name = name;
        this.phone = phone;

    }



    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}
