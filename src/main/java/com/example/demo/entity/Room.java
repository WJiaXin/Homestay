package com.example.demo.entity;

public class Room {
    private int R_id;
    private int R_hotel;
    private String R_facilities;
    private int R_type;
    private int R_price;
    private int R_numPeople;
    private String R_picture;
    private String R_norms;
    private String R_rule;
    private String R_permit;
    private String R_name;
    private String R_time;
    private String state;
    private String full;


    public String getFull() {
        return full;
    }
    public void setFull(String full) {
        this.full = full;
    }
    public String getR_name() {
        return R_name;
    }
    public void setR_name(String r_name) {
        R_name = r_name;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getR_time() {
        return R_time;
    }

    public void setR_time(String r_time) {
        R_time = r_time;
    }
    public int getR_type() {
        return R_type;
    }

    public void setR_type(int r_type) {
        R_type = r_type;
    }

    public int getR_price() {
        return R_price;
    }

    public void setR_price(int r_price) {
        R_price = r_price;
    }

    public int getR_numPeople() {
        return R_numPeople;
    }

    public void setR_numPeople(int r_numPeople) {
        R_numPeople = r_numPeople;
    }

    public String getR_picture() {
        return R_picture;
    }

    public void setR_picture(String r_picture) {
        R_picture = r_picture;
    }

    public String getR_norms() {
        return R_norms;
    }

    public void setR_norms(String r_norms) {
        R_norms = r_norms;
    }

    public String getR_rule() {
        return R_rule;
    }

    public void setR_rule(String r_rule) {
        R_rule = r_rule;
    }

    public String getR_permit() {
        return R_permit;
    }

    public void setR_permit(String r_permit) {
        R_permit = r_permit;
    }
    public String getR_facilities() {
        return R_facilities;
    }

    public void setR_facilities(String r_facilities) {
        R_facilities = r_facilities;
    }

    public int getR_hotel() {
        return R_hotel;
    }

    public void setR_hotel(int r_hotel) {
        R_hotel = r_hotel;
    }

    public int getR_id() {
        return R_id;
    }

    public void setR_id(int r_id) {
        R_id = r_id;
    }

    public Room(int r_id, int r_hotel, String r_facilities, int r_type, int r_price, int r_numPeople, String r_picture, String r_norms, String r_rule, String r_permit) {
        R_id = r_id;
        R_hotel = r_hotel;
        R_facilities = r_facilities;
        R_type = r_type;
        R_price = r_price;
        R_numPeople = r_numPeople;
        R_picture = r_picture;
        R_norms = r_norms;
        R_rule = r_rule;
        R_permit = r_permit;
    }
    public Room(){
    }
}
