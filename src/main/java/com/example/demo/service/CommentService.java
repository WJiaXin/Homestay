package com.example.demo.service;

import com.example.demo.entity.Comment;

public interface CommentService {
    abstract Comment findUserById(String C_userid);
    abstract void insertComment(Comment comment);
}
