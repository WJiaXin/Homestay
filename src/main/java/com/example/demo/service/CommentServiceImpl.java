package com.example.demo.service;

import com.example.demo.dao.CommentDao;
import com.example.demo.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentDao commentDao;
    @Override
    public Comment findUserById(String C_userid){
        return commentDao.findUserById(C_userid);
    }
    @Override
    public void insertComment(Comment comment){
        commentDao.insertComment(comment);
    }

}
