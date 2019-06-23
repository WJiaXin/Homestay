package com.example.demo.entity;




public class Order {
    private int O_id;
    private int O_user;
    private String O_name;
    private String  O_phone;
    private int O_price;
    private String O_time;
    private String O_Stime;
    private int O_room;
    private int O_account;
    private String O_Etime;
    private String state;

    public int getO_id() {
        return O_id;
    }

    public void setO_id(int o_id) {
        O_id = o_id;
    }

    public int getO_user() {
        return O_user;
    }

    public void setO_user(int o_user) {
        O_user = o_user;
    }

    public String getO_name() {
        return O_name;
    }

    public String getO_phone() {
        return O_phone;
    }

    public void setO_phone(String o_phone) {
        O_phone = o_phone;
    }

    public void setO_name(String o_name) {
        O_name = o_name;
    }

    public int getO_price() {
        return O_price;
    }

    public void setO_price(int o_price) {
        O_price = o_price;
    }


    public int getO_room() {
        return O_room;
    }

    public void setO_room(int o_room) {
        O_room = o_room;
    }

    public int getO_account() {
        return O_account;
    }

    public void setO_account(int o_account) {
        O_account = o_account;
    }
    public String getO_time() {
        return O_time;
    }

    public void setO_time(String o_time) {
        O_time = o_time;
    }

    public String getO_Stime() {
        return O_Stime;
    }

    public void setO_Stime(String o_Stime) {
        O_Stime = o_Stime;
    }

    public String getO_Etime() {
        return O_Etime;
    }

    public void setO_Etime(String o_Etime) {
        O_Etime = o_Etime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
