package com.example.demo.controller;

import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping(value = "/comment")
@Controller
public class CommentController {
    //依赖注入
    @Autowired
    CommentServiceImpl commentService;
    @RequestMapping(value = "/getComment")
    @ResponseBody
    public Comment getComment(HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User)session.getAttribute("userinfo") ;
        String user_id=user.getUser_id();
        Comment comment=commentService.findUserById(user_id);
        return comment;
    }
}
