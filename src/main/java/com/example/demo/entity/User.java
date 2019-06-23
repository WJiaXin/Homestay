package com.example.demo.entity;

public class User {
    private String user_id;
    private String user_name;
    private String user_IDcard;
    private int user_sex;
    private String user_picture;
    private String user_address;
    private int user_account;
    private  String user_pwd;


    public String getUser_id() {

        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_IDcard() {
        return user_IDcard;
    }

    public void setUser_IDcard(String user_IDcard) {
        this.user_IDcard = user_IDcard;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public int getUser_account() {
        return user_account;
    }

    public void setUser_account(int user_account) {
        this.user_account = user_account;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }
    public User(){//无参构造法

    }
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_IDcard='" + user_IDcard + '\'' +
                ", user_sex=" + user_sex +
                ", user_picture='" + user_picture + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_account=" + user_account +
                ", user_pwd='" + user_pwd + '\'' +
                '}';
    }
}
