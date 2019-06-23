package com.example.demo.controller;


import com.example.demo.entity.Homestay;
import com.example.demo.entity.Room;
import com.example.demo.service.HomestayService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/homestay")
@Controller
public class HomestayController {
    @Autowired
    HomestayService homestayService;

    @RequestMapping(value="/step1")
    public void step1(HttpServletResponse response, HttpServletRequest request, @RequestBody String obj) throws IOException {
     JSONObject json=JSONObject.fromObject(obj);
        Homestay homestay=new Homestay();
        homestay.setH_owner(json.get("owner").toString());
        homestay.setType(json.getJSONObject("homestay").get("fangyuan").toString());
        homestay.setH_name(json.getJSONObject("homestay").get("homestayName").toString());
        homestay.setH_brief(json.getJSONObject("homestay").get("homestayBrief").toString());
        homestay.setH_address(json.get("address").toString());
        homestay.setH_rule(json.get("zhengce").toString());
        homestay.setCity(json.get("city").toString());
        homestay.setDistrict(json.get("district").toString());
        homestay.setState("编辑中");
        homestay.setPicture(json.get("img").toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        homestay.setTime(df.format(new Date()));
       if(homestayService.setHomestay(homestay)==1) {
           System.out.println("成功");
           request.getSession().setAttribute("homestay",homestayService.findHomestay(homestay.getH_owner(),homestay.getTime()));
           response.getWriter().print('1');
       }
    }

    @RequestMapping(value="/step2")
    public void step2(HttpServletResponse response, HttpServletRequest request, @RequestBody String obj) throws IOException {
        JSONObject json=JSONObject.fromObject(obj);
        Homestay homestay= (Homestay)request.getSession().getAttribute("homestay");
        System.out.println("hid:"+homestay.getH_id());
        Room room=new Room();
        room.setR_permit(homestay.getH_rule());
        room.setR_hotel(homestay.getH_id());
        room.setR_type((Integer) json.get("fangxin"));
        room.setR_norms(json.get("leixing").toString());
        room.setState("编辑中");
        room.setR_name(json.get("roomName").toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        room.setR_time(df.format(new Date()));
        if(homestayService.setRoom(room)==1){
            System.out.println("room成功");
            response.getWriter().print('1');
            request.getSession().setAttribute("room",homestayService.findRoom(homestay.getH_id(),room.getR_time()));
        }
    }

    @RequestMapping(value="/step3")
    public void step3(HttpServletResponse response, HttpServletRequest request, @RequestBody String obj) throws IOException {
        JSONObject json=JSONObject.fromObject(obj);
        Room room=(Room)request.getSession().getAttribute("room");
        room.setR_facilities(json.toString());
        if(homestayService.setRoomFacility(room)==1){
            System.out.println("room2成功");
            response.getWriter().print('1');
            request.getSession().setAttribute("room",room);
        }
    }
    @RequestMapping(value="/step4")
    public void step4(HttpServletResponse response, HttpServletRequest request, @RequestBody String obj) throws IOException {
        JSONObject json=JSONObject.fromObject(obj);
        Room room=(Room)request.getSession().getAttribute("room");
        room.setR_picture(json.toString());
        if(homestayService.setRoomImg(room)==1){
            System.out.println("room3成功");
            response.getWriter().print('1');
            request.getSession().setAttribute("room",room);
        }
    }
    @RequestMapping(value="/step5")
    public void step5(HttpServletResponse response, HttpServletRequest request, @RequestBody String obj) throws IOException {
        JSONObject json=JSONObject.fromObject(obj);
        System.out.println(obj);
        Room room=(Room)request.getSession().getAttribute("room");
        room.setR_price(Integer.parseInt(json.get("price").toString()));
        room.setR_numPeople(Integer.parseInt(json.get("yiju").toString()));
        room.setR_rule(json.get("clear").toString());
        room.setState("审核中");
        if(homestayService.setRoomPrice(room)==1&&homestayService.setHstate(room.getR_hotel(),"审核中")==1){
            System.out.println("room4成功");
            response.getWriter().print('1');
            request.getSession().setAttribute("room",room);
        }
    }
    @RequestMapping(value="/getUHomestay")
    @ResponseBody
    public List<Homestay> getUHomestay(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<Homestay> homestayList=homestayService.findIdHomestay("17738505200");
        System.out.println("------------价格:"+homestayList.get(1).getPrice());
        return homestayList;
    }
    @RequestMapping(value="/getHRoom")
    @ResponseBody
    public List<Room> getHRoom(HttpServletResponse response, HttpServletRequest request,@RequestParam("hid") int Hid) throws IOException {
        System.out.println(Hid);
        List<Room> roomList=homestayService.findIdRoom(32);
        System.out.println("------------价格:"+roomList.get(0).getR_price());
        return roomList;
    }

    @RequestMapping(value="/home")
    @ResponseBody
    public List<Homestay> home(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<Homestay> homestayList=homestayService.findAllHomestay();
        System.out.println("------------价格:"+homestayList.get(0).getH_name());
        return homestayList;
    }
    @RequestMapping(value="/test")
    public void test(HttpServletResponse response, HttpServletRequest request){
        System.out.println("进入");

    }

}
