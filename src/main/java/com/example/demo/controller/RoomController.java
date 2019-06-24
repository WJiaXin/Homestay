package com.example.demo.controller;

import com.example.demo.dao.HotelDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.dao.RoomDao;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;


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
    @RequestMapping(value = "/getApplyRoom")  //得到申请的room信息
    public void getApplyRoom(HttpServletResponse response ,HttpServletRequest request ,HttpSession session) throws  Exception{
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            String  nothing = request.getParameter("page");
            System.out.println(nothing);
            System.out.println("getApplyRoom测试");
        int page = Integer.valueOf(nothing);
        int num=0;
        if(page == 1){
            num=0;
        }else{
            num = (page-1)*5;
        }
        int allapplyroomnum = roomDao.getRoomApplyNum();
        List<Room> getapplyRoominfo= roomDao.getApplyRoom(num);
        List<JSONObject> list = new ArrayList<>();
        for (Room room :getapplyRoominfo ){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("permit",room.getR_permit());
            jsonObject.put("roomname",room.getR_name());
            jsonObject.put("roomprice",room.getR_price());
            int type = Integer.valueOf(room.getR_type());
            if (type==0) {
                jsonObject.put("roomtype", "整套房间");
            }else{
                jsonObject.put("roomtype", "独立单间");
            }
            String[] shesi = room.getR_facilities().split(",");
            String[] rule1 = shesi[19].split(":");
            String[] rule2 = shesi[20].split(":");
            String[] rule3 = shesi[21].split(":");
            String[] rule4 = rule3[1].split("}");
            if (Integer.valueOf(rule1[1])==1){
            jsonObject.put("pet","允许携带宠物");
           }else{
               jsonObject.put("pet","不允许携带宠物");
           }
            if (Integer.valueOf(rule2[1])==1){
                jsonObject.put("meeting","允许聚会");
            }else{
                jsonObject.put("meeting","不允许聚会");
            }
            if (Integer.valueOf(rule4[0])==1){
                jsonObject.put("cook","允许做饭");
            }else{
                jsonObject.put("cook","不允许做饭");
            }
            String[] pic = room.getR_picture().split(",");
            String[] piv1 = pic[0].split(":");
            char[] pic1 = piv1[1].toCharArray();
            char[] pic2 = new char[26];
            for(int j=2;j<28;j++){
                pic2[j-2]=pic1[j];
            }
            jsonObject.put("time",room.getR_time());
            jsonObject.put("roomid",room.getR_id());
            String[] norms = room.getR_norms().split(",");
            String[] shi = norms[0].split(":");
            String[] ting = norms[1].split(":");
            String[] wei = norms[2].split(":");
            String[] chu = norms[3].split(":");
            String[] mianji = norms[5].split(":");
            if(mianji[1].toCharArray().length>2) {
                char[] mianjia = new char[mianji[1].toCharArray().length];
                for (int j=1;j<mianji[1].toCharArray().length-1;j++){
                    mianjia[j-1]=mianji[1].toCharArray()[j];
                }
                jsonObject.put("roommianji",String.valueOf(mianjia)+"㎡");
            }else{
               jsonObject.put("roommianji","未知");
            }
            String huxin = shi[1].toCharArray()[1]+"室"+ting[1].toCharArray()[1]+"厅"+wei[1].toCharArray()[1]+"卫"+chu[1].toCharArray()[1]+"厨";
            jsonObject.put("huxing",huxin);
            String roomweiyu = norms[13].split(":")[1];
            if (roomweiyu.toCharArray().length>3) {
                char[] roomweiyua = new char[roomweiyu.toCharArray().length - 3];
                for (int j = 1; j < roomweiyu.toCharArray().length - 2; j++) {
                    roomweiyua[j - 1] = roomweiyu.toCharArray()[j];
                }
                jsonObject.put("roomweiyu",String.valueOf(roomweiyua));
            }else{
                jsonObject.put("roomweiyu","未知");
            }
            jsonObject.put("picture",String.valueOf(pic2));
            jsonObject.put("state",room.getState());
            list.add(jsonObject);
        }
        if(allapplyroomnum%5==0){
            num=allapplyroomnum/5;
        }else{
            num=allapplyroomnum/5+1;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allapplyroomnum",getapplyRoominfo.size());
        jsonObject.put("num",num);
        list.add(jsonObject);
        JSONArray jsonArray = JSONArray.fromObject( list );
        System.out.println(jsonArray.toString());
        printWriter.print(jsonArray.toString());
    }
    @RequestMapping(value ="/updatdeRoomArriy")
    public void updateRoomApply(HttpServletResponse response ,HttpServletRequest request)throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        String stringid = request.getParameter("id");
        String state =request.getParameter("state");
        int id = Integer.valueOf(stringid);
        System.out.println(id+"<-id,state->"+state);
        roomDao.updataRoomState(id,state);

    }
    @RequestMapping(value = "/getApplyRoomId")
    public String getApplyRoomId(HttpServletRequest request ,HttpSession session){
        String id = request.getParameter("id");
        session.setAttribute("applyroomid",id);
        System.out.println("查看房间id:"+id);
        return "redirect:/html/checkRoom.html";
    }
    @RequestMapping(value = "/getOneApplyRoom")
    @ResponseBody
    public JSONObject  getOneApplyRoom(HttpServletResponse response ,HttpServletRequest request ,HttpSession session) throws  Exception{
        String  nothing = request.getParameter("qq");
        System.out.println(nothing);
        System.out.println("getOneApplyRoom测试");
        String str =(String)session.getAttribute("applyroomid");
        int roomid = Integer.valueOf(str);
        Room room = roomDao.getRoomByRID(roomid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("peoplenum",room.getR_numPeople());
        jsonObject.put("roomname",room.getR_name());
        jsonObject.put("roomprice",room.getR_price());
        jsonObject.put("someprice",room.getR_rule());
        int type = Integer.valueOf(room.getR_type());
        if (type==0) {
            jsonObject.put("roomtype", "整套房间");
        }else{
            jsonObject.put("roomtype", "独立单间");
        }
        String[] pic = room.getR_picture().split(",");
        for(int i =0;i<pic.length;i++) {
            String[] piv1 = pic[i].split(":");
            char[] pic1 = piv1[1].toCharArray();
            String str12 = "img" + (4+i);
            if (piv1[1].toCharArray().length > 8) {
                char[] pic2 = new char[piv1[1].toCharArray().length - 4];
                for (int j = 2; j < piv1[1].toCharArray().length - 2; j++) {
                    pic2[j - 2] = pic1[j];
                }
                jsonObject.put(str12, String.valueOf(pic2));
            } else {
                jsonObject.put(str12, "无");
            }
        }
        jsonObject.put("time",room.getR_time());
        jsonObject.put("roomid",room.getR_id());
        String[] norms = room.getR_norms().split(",");
        String[] shi = norms[0].split(":");
        String[] ting = norms[1].split(":");
        String[] wei = norms[2].split(":");
        String[] chu = norms[3].split(":");
        String[] yangtai = norms[4].split(":");
        String[] mianji = norms[5].split(":");
        String[] bigbed2 = norms[6].split(":");
        String[] bigbed = norms[7].split(":");
        String[] bed = norms[8].split(":");
        String[] shangxia = norms[9].split(":");
        String[] shafa= norms[10].split(":");
        String[] tatami = norms[11].split(":");
        String[] qita = norms[12].split(":");
        jsonObject.put("bigbed2",bigbed2[2].toCharArray()[1]);
        jsonObject.put("bigbed",bigbed[1].toCharArray()[1]);
        jsonObject.put("bed",bed[1].toCharArray()[1]);
        jsonObject.put("shangxia",shangxia[1].toCharArray()[1]);
        jsonObject.put("shafa",shafa[1].toCharArray()[1]);
        jsonObject.put("tatami",tatami[1].toCharArray()[1]);
        jsonObject.put("qita",qita[1].toCharArray()[1]);
        jsonObject.put("wei",wei[1].toCharArray()[1]);
        jsonObject.put("chu",chu[1].toCharArray()[1]);
        jsonObject.put("yangtai",yangtai[1].toCharArray()[1]);
        jsonObject.put("shi",shi[1].toCharArray()[1]);
        jsonObject.put("ting",ting[1].toCharArray()[1]);
        if(mianji[1].toCharArray().length>2) {
            char[] mianjia = new char[mianji[1].toCharArray().length];
            for (int j=1;j<mianji[1].toCharArray().length-1;j++){
                mianjia[j-1]=mianji[1].toCharArray()[j];
            }
            jsonObject.put("roommianji",String.valueOf(mianjia)+"㎡");
        }else{
            jsonObject.put("roommianji","未知");
        }
        String roomweiyu = norms[13].split(":")[1];
        if (roomweiyu.toCharArray().length>3) {
            char[] roomweiyua = new char[roomweiyu.toCharArray().length - 3];
            for (int j = 1; j < roomweiyu.toCharArray().length - 2; j++) {
                roomweiyua[j - 1] = roomweiyu.toCharArray()[j];
            }
            jsonObject.put("roomweiyu",String.valueOf(roomweiyua));
        }else{
            jsonObject.put("roomweiyu","未知");
        }
        System.out.println(jsonObject.toString());
        return  jsonObject;
    }
    @RequestMapping(value = "/agreeRoomApply")
    @ResponseBody
    public String agreeRoomApply(HttpServletRequest request ,HttpSession session){
        String id = request.getParameter("qq");
        System.out.println(id);
        String str =(String)session.getAttribute("applyroomid");
        int roomid = Integer.valueOf(str);
        roomDao.updataRoomState(roomid,"已上线");
        System.out.println("审核通过");
        return "ok";
    }
    @RequestMapping(value = "/notAgreeRoomApply")
    @ResponseBody
    public String notAgreeRoomApply(HttpServletRequest request ,HttpSession session){
        String id = request.getParameter("qq");
        System.out.println(id);
        String str =(String)session.getAttribute("applyroomid");
        int roomid = Integer.valueOf(str);
        roomDao.updataRoomState(roomid,"已拒绝");
        System.out.println("审核未通过");
        return "ok";
    }
   @RequestMapping(value = "getAllHotelApply")
    public void getAllHotelApply(HttpServletResponse response ,HttpServletRequest request ,HttpSession session) throws  Exception{
       response.setContentType("text/html;charset=UTF-8");
       PrintWriter printWriter = response.getWriter();
       String  nothing = request.getParameter("page");
       System.out.println(nothing);
       System.out.println("getAllHotelApply测试");
       int page = Integer.valueOf(nothing);
       int num=0;
       if(page == 1){
           num=0;
       }else{
           num = (page-1)*5;
       }
       int allAllHotelApplyNum = hotelDao.getHotelApplyNum(); //用来计算页数
       List<Hotel> hotel = hotelDao.getApplyHotel(num);
       List<JSONObject> list = new ArrayList<>();
       for(Hotel hotel1 :hotel){
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("name",hotel1.getH_name());
           String[] string1 = hotel1.getH_address().split(":");                                     //得到地址
           if (string1[2].toCharArray().length>3){
               char[] char2 = new char[string1[2].toCharArray().length-3];
               for(int i=1;i<string1[2].toCharArray().length-2;i++){
                   char2[i-1] = string1[2].toCharArray()[i];
               }
               jsonObject.put("hoteladdress",String.valueOf(char2));
           }else {
               jsonObject.put("hoteladdress","无");
           }
           int price = roomDao.getMinRoomPrice(hotel1.getH_id());                                              //得到规则
           jsonObject.put("price",String.valueOf(price));
           string1=hotel1.getH_rule().split(":");
           int chengdu = string1[6].toCharArray().length+string1[7].toCharArray().length;
           if (chengdu>3){
               char[] char2 = new char[chengdu-3];
               for(int i=1;i<chengdu-2;i++){
                   if(i<string1[6].toCharArray().length) {
                       char2[i - 1] = string1[6].toCharArray()[i];
                   }else if (i==string1[6].toCharArray().length){
                       char2[i-1] = ':';
                   }else{
                       char2[i-1] = string1[7].toCharArray()[i-string1[6].length()-1];
                   }
               }
               jsonObject.put("hotelrule",String.valueOf(char2));
           }else {
               jsonObject.put("hotelrule","无");
           }
          String[] string =hotel1.getPicture().split(",");                                           //得到图片
           string1 = string[0].split(":");
           if (string1[1].toCharArray().length>8){
               char[] char2 = new char[string1[1].toCharArray().length-4];
               for(int i=2;i<string1[1].toCharArray().length-2;i++){
                   char2[i-2] = string1[1].toCharArray()[i];
               }
               jsonObject.put("picture",String.valueOf(char2));
           }else {
               jsonObject.put("picture","room6.jpg");
           }
           jsonObject.put("hotelid",hotel1.getH_id());
           list.add(jsonObject);
       }
       if(allAllHotelApplyNum%5==0){
           num=allAllHotelApplyNum/5;
       }else{
           num=allAllHotelApplyNum/5+1;
       }
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("getHotelNum",hotel.size());
       jsonObject.put("page",num);
       list.add(jsonObject);
       JSONArray jsonArray = JSONArray.fromObject( list );
       System.out.println(jsonArray.toString());
       printWriter.print(jsonArray.toString());
   }
    @RequestMapping(value = "/getApplyHotelId")
    public String getApplyHotelId(HttpServletRequest request ,HttpSession session){
        String id = request.getParameter("id");
        session.setAttribute("applyhotelid",id);
        System.out.println("查看hotelid:"+id);
        return "redirect:/html/checkHomestay.html";
    }
    @RequestMapping(value = "/getOneApplyHotel")
    @ResponseBody
    public JSONObject  getOneApplyHotel(HttpServletResponse response ,HttpServletRequest request ,HttpSession session) throws  Exception {
        String nothing = request.getParameter("qq");
        System.out.println(nothing);
        System.out.println("getOneApplyHotel测试");
        String str = (String) session.getAttribute("applyhotelid");
        int hotelid = Integer.valueOf(str);
        Hotel hotel = hotelDao.getHotelById(hotelid);
        List<Room> listroom = roomDao.getRoomByHid(hotelid);
        if (listroom.size() > 0) {
            Room room = listroom.get(0);
            session.setAttribute("roomidh",room.getR_id());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hotelname",hotel.getH_name());
            String[] hoteladd1 = hotel.getH_address().split(":");
            if(hoteladd1[2].toCharArray().length>3) {
                char[] hoteladd2 = new char[hoteladd1[2].toCharArray().length-3];
                for (int i = 1; i < hoteladd1[2].toCharArray().length - 2; i++) {
                    hoteladd2[i-1]=hoteladd1[2].toCharArray()[i];
                }
                jsonObject.put("hoteladdd",String.valueOf(hoteladd2));
            }else{
                jsonObject.put("hoteladdd","无");
            }
            String[] hotelpic = hotel.getPicture().split(",");
            for(int i=0;i<hotelpic.length;i++){
                String[] hotelpic1 = hotelpic[i].split(":");
                char[] pic1 = hotelpic1[1].toCharArray();
                String str12 = "img" +(1+ i);
                if (hotelpic1[1].toCharArray().length > 8) {
                    char[] pic2 = new char[hotelpic1[1].toCharArray().length - 4];
                    for (int j = 2; j < hotelpic1[1].toCharArray().length - 2; j++) {
                        pic2[j - 2] = pic1[j];
                    }
                    jsonObject.put(str12, String.valueOf(pic2));
                } else {
                    jsonObject.put(str12, "无");
                }
            }
            jsonObject.put("hotelbrief",hotel.getH_brief());
            jsonObject.put("peoplenum", room.getR_numPeople());
            jsonObject.put("roomname", room.getR_name());
            jsonObject.put("roomprice", room.getR_price());
            jsonObject.put("someprice", room.getR_rule());
            int type = Integer.valueOf(room.getR_type());
            if (type == 0) {
                jsonObject.put("roomtype", "整套房间");
            } else {
                jsonObject.put("roomtype", "独立单间");
            }
            String[] pic = room.getR_picture().split(",");
            for (int i = 0; i < pic.length; i++) {
                String[] piv1 = pic[i].split(":");
                char[] pic1 = piv1[1].toCharArray();
                String str12 = "img" + (4 + i);
                if (piv1[1].toCharArray().length > 8) {
                    char[] pic2 = new char[piv1[1].toCharArray().length - 4];
                    for (int j = 2; j < piv1[1].toCharArray().length - 2; j++) {
                        pic2[j - 2] = pic1[j];
                    }
                    jsonObject.put(str12, String.valueOf(pic2));
                } else {
                    jsonObject.put(str12, "无");
                }
            }
            jsonObject.put("time", room.getR_time());
            jsonObject.put("roomid", room.getR_id());
            String[] norms = room.getR_norms().split(",");
            String[] shi = norms[0].split(":");
            String[] ting = norms[1].split(":");
            String[] wei = norms[2].split(":");
            String[] chu = norms[3].split(":");
            String[] yangtai = norms[4].split(":");
            String[] mianji = norms[5].split(":");
            String[] bigbed2 = norms[6].split(":");
            String[] bigbed = norms[7].split(":");
            String[] bed = norms[8].split(":");
            String[] shangxia = norms[9].split(":");
            String[] shafa = norms[10].split(":");
            String[] tatami = norms[11].split(":");
            String[] qita = norms[12].split(":");
            jsonObject.put("bigbed2", bigbed2[2].toCharArray()[1]);
            jsonObject.put("bigbed", bigbed[1].toCharArray()[1]);
            jsonObject.put("bed", bed[1].toCharArray()[1]);
            jsonObject.put("shangxia", shangxia[1].toCharArray()[1]);
            jsonObject.put("shafa", shafa[1].toCharArray()[1]);
            jsonObject.put("tatami", tatami[1].toCharArray()[1]);
            jsonObject.put("qita", qita[1].toCharArray()[1]);
            jsonObject.put("wei", wei[1].toCharArray()[1]);
            jsonObject.put("chu", chu[1].toCharArray()[1]);
            jsonObject.put("yangtai", yangtai[1].toCharArray()[1]);
            jsonObject.put("shi", shi[1].toCharArray()[1]);
            jsonObject.put("ting", ting[1].toCharArray()[1]);
            if (mianji[1].toCharArray().length > 2) {
                char[] mianjia = new char[mianji[1].toCharArray().length];
                for (int j = 1; j < mianji[1].toCharArray().length - 1; j++) {
                    mianjia[j - 1] = mianji[1].toCharArray()[j];
                }
                jsonObject.put("roommianji", String.valueOf(mianjia) + "㎡");
            } else {
                jsonObject.put("roommianji", "未知");
            }
            String roomweiyu = norms[13].split(":")[1];
            if (roomweiyu.toCharArray().length > 3) {
                char[] roomweiyua = new char[roomweiyu.toCharArray().length - 3];
                for (int j = 1; j < roomweiyu.toCharArray().length - 2; j++) {
                    roomweiyua[j - 1] = roomweiyu.toCharArray()[j];
                }
                jsonObject.put("roomweiyu", String.valueOf(roomweiyua));
            } else {
                jsonObject.put("roomweiyu", "未知");
            }
            System.out.println(jsonObject.toString());
            return jsonObject;
        }else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("asd","qwer");
            return jsonObject;
        }
    }
    @RequestMapping(value = "/notAgreehotelApply")
    @ResponseBody
    public String notAgreehotelApply(HttpServletRequest request ,HttpSession session){
        String id = request.getParameter("qq");
        System.out.println(id);
        String str =(String)session.getAttribute("applyhotelid");
        int hotelid = Integer.valueOf(str);
        List<Room> rooms = roomDao.getRoomByHid(hotelid);
        for(int i=0;i<rooms.size();i++){
            roomDao.updataRoomState(rooms.get(i).getR_id(),"已拒绝");
            System.out.println("审核未通过id"+rooms.get(i).getR_id());
        }
        hotelDao.updataHotelState(hotelid,"已拒绝");
        System.out.println("审核未通过hotelid"+str);
        System.out.println("审核未通过");
        return "ok";
    }
    @RequestMapping(value = "/agreehotelApply")
    @ResponseBody
    public String agreehotelApply(HttpServletRequest request ,HttpSession session){
        String id = request.getParameter("qq");
        System.out.println(id);
        String str =(String)session.getAttribute("applyhotelid");
        int hotelid = Integer.valueOf(str);
        int roomid = (int) session.getAttribute("roomidh");
        roomDao.updataRoomState(roomid,"已上线");
        hotelDao.updataHotelState(hotelid,"已上线");
        System.out.println("审核通过hotelid"+str);
        System.out.println("审核通过roomid"+roomid);
        return "ok";
    }

}

