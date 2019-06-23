package com.example.demo.entity;

import java.util.Date;

public class Comment {
    private int C_id;
    private String C_userid;
    private int C_order;
    private String C_content;
    private Date C_time;
    private float C_score;
    private String C_picture;

    public Date getC_time() {
        return C_time;
    }

    public void setC_time(Date c_time) {
        C_time = c_time;
    }

    public int getC_id() {
        return C_id;
    }

    public String getC_picture() {
        return C_picture;
    }

    public void setC_picture(String c_picture) {
        C_picture = c_picture;
    }

    public String getC_content() {
        return C_content;
    }

    public void setC_content(String c_content) {
        C_content = c_content;
    }

    public int getC_order() {
        return C_order;
    }

    public void setC_order(int c_order) {
        C_order = c_order;
    }

    public void setC_id(int c_id) {
        C_id = c_id;
    }

    public float getC_score() {
        return C_score;
    }

    public void setC_score(float c_score) {
        C_score = c_score;
    }

    public String getC_userid() {
        return C_userid;
    }

    public void setC_userid(String c_userid) {
        C_userid = c_userid;
    }
    public Comment(){//无参构造
    }
    @Override
    public String toString() {
        return "Coment{" +
                "C_id=" + C_id +
                ", C_userid='" + C_userid + '\'' +
                ", C_order='" + C_order + '\'' +
                ", C_content=" + C_content +
                ", C_time='" + C_time + '\'' +
                ", C_score='" + C_score + '\'' +
                ", C_picture='" + C_picture + '\'' +
                '}';
    }
}
