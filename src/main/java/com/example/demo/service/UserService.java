package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {

    abstract void insertUser(User user);
    abstract User findUserById(String user_id);
    abstract int  verification(String user_id);
    abstract void upName(String user_name,String user_id);//用于修改用户名
    abstract void upUserImg(String user_picture,String user_id);//用于修改照片
    abstract void upPwd(String user_pwd,String user_id);//用于修改密码

}
