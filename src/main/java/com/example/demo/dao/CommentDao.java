package com.example.demo.dao;

import com.example.demo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentDao {
    Comment findUserById(String C_userid);//通过用户id查找用户的评论信息。
    void insertComment(Comment comment);//将用户评论插入到数据库
}
