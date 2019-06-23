package com.example.demo.controller;

import com.example.demo.dao.HotelDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.dao.RoomDao;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


@RequestMapping(value = "/room")
@Controller
public class RoomController {

    @Autowired
    RoomDao roomDao;
    @Autowired
    HotelDao hotelDao;
    @Autowired
    OrderDao orderDao;

    @GetMapping(value = "/getRoomId")   //得到id并跳转页面
    public String getRoomById(HttpSession httpSession, HttpServletRequest request) throws Exception{
        String id = request.getParameter("id");
        System.out.println(id);
        int room_id = Integer.valueOf(id);
        httpSession.setAttribute("room_id",room_id);
        return "redirect:/html/room.html";
    }
    @RequestMapping(value = "/getRoomInfoById")  //通过房间id得到房间信息
    public  void  getRootInfoById(HttpServletResponse response ,HttpServletRequest request ,HttpSession session) throws  Exception{
        System.out.println("getRoomInfoById");
        PrintWriter printWriter = response.getWriter();
        int id = (int)session.getAttribute("room_id");
        Room room = roomDao.getRoomByRID(id);
        String  nothing = request.getParameter("who");
        JSONObject json = JSONObject.fromObject(room);
        System.out.println(json.toString());
        printWriter.print(json.toString());
    }
    @RequestMapping(value = "/test")
    public  void  test(){
       int num = orderDao.getOrderNum(1);
            System.out.println(num);
    }
}
