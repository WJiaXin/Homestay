package com.example.demo.controller;

import com.example.demo.dao.HotelDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.dao.RoomDao;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Order;
import com.example.demo.entity.Room;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


@RequestMapping(value = "/order")
@Controller
public class OrderController {
    @Autowired
    OrderDao orderDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    HotelDao hotelDao;

    @RequestMapping( "order_getinfo")   //获取订单房间信息
     @ResponseBody
        public void order_getinfo(@RequestParam("price") String price, @RequestParam("datec" )String daterangeC, HttpSession session, HttpServletResponse response) throws  Exception{
            session.setAttribute("price",price);
            String[] str = daterangeC.split(" ");
            String stime = str[0];
            String etime = str[2];
            session.setAttribute("stime",stime);
            session.setAttribute("etime",etime);
            System.out.println(daterangeC+"    t2:"+etime);
            System.out.println("gettinfo  is end   !!!");

    }

    @RequestMapping("addOrder")
    @ResponseBody    //添加订单
    public String addOrder(@RequestParam("name") String name,@RequestParam("phone") String phone,HttpSession session) throws  Exception{//获取入住信息并添加订单
        int price =Integer.valueOf((String)session.getAttribute("price"));
        String stime = (String)session.getAttribute("stime");
        String etimr = (String)session.getAttribute("etime");
        Order order = new Order();
        order.setO_user(1);
        order.setO_name(name);
        order.setO_phone(phone);
        order.setO_price(price);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setO_time( df.format(new Date()));
        order.setO_Stime(stime);
        order.setO_Etime(etimr);
        order.setO_room(1);
        order.setO_account(1);
        order.setState("已下单");
        orderDao.addOrder(order);
        System.out.println(" userid:"+order.getO_user()+
                " name:"+order.getO_name()+
                " phone:"+order.getO_phone()+
                " price:"+order.getO_price()+
                " t1:"+order.getO_time()+
                " t2:"+order.getO_Stime()+
                " t3:"+order.getO_Etime()+
                " roon:"+order.getO_room()+
                " account:"+order.getO_account()+
                " state:"+order.getState()
        );
        return "redirect:/html/P_order.html";

    }
    @GetMapping(value = "/deleteOrderById")//通过session中的id删除订单
    public  String deleteOrderById(HttpSession session,HttpServletRequest request){
        String str = (String) session.getAttribute("getOneOrderInfoId");
        int id = Integer.valueOf(str);
        System.out.println("删除："+id);
        orderDao.deleteOrderById(id);
        return "redirect:/html/Personal.html";

    }
    @GetMapping(value = "/myOrder")                //无意义
    public  String myOrder(HttpSession session){              //读取个人订单，注意个人id还需修改
        List<Order> myorder = orderDao.myOrder(1);
        List<Order> myorder1 = new ArrayList<Order>();
        List<Order> myorder2 = new ArrayList<Order>();
        List<Order> myorder3 = new ArrayList<Order>();
        for (int i = 0;i < myorder.size() ; i++){
            if(myorder.get(i).getState().equals("已预订")){
                myorder1.add(myorder.get(i));
            }
            else if(myorder.get(i).getState().equals("待评价")){
                myorder2.add(myorder.get(i));
            }
            else{
                myorder3.add(myorder.get(i));
            }
        }
        session.setAttribute("myorder",myorder);
        session.setAttribute("myorder1",myorder1);
        session.setAttribute("myorder2",myorder2);
        session.setAttribute("myorder3",myorder3);
        System.out.println(myorder.size()+"!!!!!!1:"+myorder1.size()+"@@@2："+myorder3.size());
        //return "redirect:/html/personal.html";
        return "redirect:/html/P_order.html";
    }
    //数据转化
    public List<JSONObject> getAllInfo( List<Map<String, Object>> sqlresult){
       List <JSONObject> objectlist = new ArrayList<JSONObject>();
        for (Map<String, Object> one_sqlresult : sqlresult) {
            JSONObject object = new JSONObject();
            object.put("o_Etime", String.valueOf(one_sqlresult.get("O_Etime")));
            object.put("o_Stime", String.valueOf(one_sqlresult.get("O_Stime")));
            object.put("o_account", one_sqlresult.get("O_account"));
            object.put("o_id", one_sqlresult.get("O_id"));
            object.put("o_name", one_sqlresult.get("O_name"));
            object.put("o_phone", one_sqlresult.get("O_phone"));
            object.put("o_price", one_sqlresult.get("O_price"));
            object.put("o_room", one_sqlresult.get("O_room"));
            object.put("o_time", String.valueOf(one_sqlresult.get("O_time")));
            object.put("o_user", one_sqlresult.get("O_user"));
            object.put("state", one_sqlresult.get("state"));
            object.put("hotelname", one_sqlresult.get("R_name"));
            object.put("rule", one_sqlresult.get("R_rule"));
            objectlist.add(object);
        }
        return  objectlist;
    }   //数据转化
    @RequestMapping(value ="/getAllOrder")    // 得到用户全部订单
    public  void  getAllOrder(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        String  nothing = request.getParameter("page");
        System.out.println(nothing);
        int page = Integer.valueOf(nothing);
        int star = 0;
        if(page>1){
            star=(page-1)*5 ;
        }
        int ordernum = orderDao.getOrderNum(1);
        int num=0;
        if(ordernum%5==0){
            num=ordernum/5;
        }else{
            num=ordernum/5+1;
        }
        System.out.println("用户全部订单页数："+num);
        List<Map<String, Object>> sqlresult= orderDao.getAllOrder(1,star);
        List<JSONObject> allorder = getAllInfo(sqlresult);
        Map<String ,JSONObject> map = new HashMap<String, JSONObject>() ;
            for(int i = 0;i<allorder.size();i++){
                map.put(String.valueOf(i),allorder.get(i));
            }
            JSONObject object = new JSONObject();
            object.put("num",num);
            object.put("ordernum",allorder.size());
            map.put(String.valueOf(allorder.size()),object);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json.toString());
        printWriter.print(json.toString());
    }
    @RequestMapping(value = "getBookingOrder") //得到预定的订单
    public  void  getBookingOrder(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        String  nothing = request.getParameter("page");
        System.out.println(nothing);
        int page = Integer.valueOf(nothing);
        int star = 0;
        if(page>1){
            star=(page-1)*5 ;
        }
        int ordernum = orderDao.getAfterNum(1,"已下单","已预订");
        int num=0;
        if(ordernum%5==0){
            num=ordernum/5;
        }else{
            num=ordernum/5+1;
        }
        System.out.println("预定的订单页数："+num);
        List<Map<String, Object>> sqlresul = orderDao.getBookingOrder(1,"已下单","已预订",star);
        List<JSONObject> allorder = getAllInfo(sqlresul);
        Map<String ,JSONObject> map = new HashMap<String, JSONObject>() ;
        for(int i = 0;i<allorder.size();i++){
            map.put(String.valueOf(i),allorder.get(i));
        }
        JSONObject object = new JSONObject();
        object.put("num",num);
        object.put("ordernum",allorder.size());
        map.put(String.valueOf(allorder.size()),object);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json.toString());
        printWriter.print(json.toString());
    }
    @RequestMapping(value = "getEndOrder") //得到已完成的订单
    public  void  getEndOrder(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        String  nothing = request.getParameter("page");
        System.out.println(nothing);
        int page = Integer.valueOf(nothing);
        int star = 0;
        if(page>1){
            star=(page-1)*5;
        }
        int ordernum = orderDao.getEndNum(1,"已完成");
        int num=0;
        if(ordernum%5==0){
            num=ordernum/5;
        }else{
            num=ordernum/5+1;
        }
        System.out.println("已完成的订单页数："+num);
        List<Map<String, Object>> sqlresul = orderDao.getEndOrder(1,"已完成",star);
        List<JSONObject> allorder = getAllInfo(sqlresul);
        Map<String ,JSONObject> map = new HashMap<String, JSONObject>() ;
        for(int i = 0;i<allorder.size();i++){
            map.put(String.valueOf(i),allorder.get(i));
        }
        JSONObject object = new JSONObject();
        object.put("num",num);
        object.put("ordernum",allorder.size());
        map.put(String.valueOf(allorder.size()),object);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json.toString());
        printWriter.print(json.toString());
    }
    @RequestMapping(value = "getAfterOrder") //得到售后的订单
    public  void  getAfterOrder(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        String  nothing = request.getParameter("page");
        System.out.println(nothing);
        int page = Integer.valueOf(nothing);
        int star = 0;
        if(page>1){
            star=(page-1)*5 ;
        }
        int ordernum = orderDao.getAfterNum(1,"已取消","已退款");
        int num=0;
        if(ordernum%5==0){
            num=ordernum/5;
        }else{
            num=ordernum/5+1;
        }
        System.out.println("售后的订单页数："+num);
        List<Map<String, Object>> sqlresul = orderDao.getBookingOrder(1,"已取消","已退款",star);
        List<JSONObject> allorder = getAllInfo(sqlresul);
        Map<String ,JSONObject> map = new HashMap<String, JSONObject>() ;
        for(int i = 0;i<allorder.size();i++){
            map.put(String.valueOf(i),allorder.get(i));
        }
        JSONObject object = new JSONObject();
        object.put("num",num);
        object.put("ordernum",allorder.size());
        map.put(String.valueOf(allorder.size()),object);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json.toString());
        printWriter.print(json.toString());
    }
    @RequestMapping(value = "cancelOrderById")
    @ResponseBody  //取消订单
    public String cancelOrderById( HttpServletRequest request, HttpServletResponse response){
        String str = request.getParameter("cancelid");
        int id = Integer.valueOf(str);
        System.out.println("取消订单id:"+str);
        orderDao.updateOrderState(id,"已取消");
        return"ok";
    }
    @RequestMapping(value = "getHOrder")//得到房主全部订单
    public void  getHOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        String  nothing = request.getParameter("page");
        System.out.println(nothing);
        int page = Integer.valueOf(nothing);
        int star = 0;
        if(page>1){
            star=(page-1)*5 ;
        }
        int ordernum = orderDao.getAllOrderForHNum(1);
        int num=0;
        if(ordernum%5==0){
            num=ordernum/5;
        }else{
            num=ordernum/5+1;
        }
        System.out.println("房主全部订单页数："+num);
        List<Map<String, Object>> sqlresult= orderDao.getAllOrderForH(1,star);
        List<JSONObject> allorder = getAllInfo(sqlresult);
        Map<String ,JSONObject> map = new HashMap<String, JSONObject>() ;
        for(int i = 0;i<allorder.size();i++){
            map.put(String.valueOf(i),allorder.get(i));
        }
        JSONObject object = new JSONObject();
        object.put("num",num);
        object.put("ordernum",allorder.size());
        map.put(String.valueOf(allorder.size()),object);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json.toString());
        printWriter.print(json.toString());
    }
    @RequestMapping(value = "getOneTypeOrder") //得到一种的订单
    public  void  getOneTypeOrder(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        String  nothing = request.getParameter("page");
        String state = request.getParameter("state");
        System.out.println(nothing);
        System.out.println(state);
        int page = Integer.valueOf(nothing);
        int star = 0;
        if(page>1){
            star=(page-1)*5;
        }
        int ordernum = orderDao.getOneTypeOrderNum(1,state);
        int num=0;
        if(ordernum%5==0){
            num=ordernum/5;
        }else{
            num=ordernum/5+1;
        }
        System.out.println("一种的订单页数："+num);
        List<Map<String, Object>> sqlresul = orderDao.getOneTypeOrder(1,state,star);
        List<JSONObject> allorder = getAllInfo(sqlresul);
        Map<String ,JSONObject> map = new HashMap<String, JSONObject>() ;
        for(int i = 0;i<allorder.size();i++){
            map.put(String.valueOf(i),allorder.get(i));
        }
        JSONObject object = new JSONObject();
        object.put("num",num);
        object.put("ordernum",allorder.size());
        map.put(String.valueOf(allorder.size()),object);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json.toString());
        printWriter.print(json.toString());
    }
    @RequestMapping(value = "refuseOrderById")//拒绝用户订单
    public void  refuseOrderById( HttpServletRequest request, HttpServletResponse response){
        String str = request.getParameter("refuseOrderid");
        int id = Integer.valueOf(str);
        System.out.println("一种的订单id:"+str);
        orderDao.updateOrderState(id,"已退款");
    }
    @RequestMapping(value = "transit")   //将查看订单的id放在session中
    public String transit(HttpServletRequest request,HttpSession session){
        String str = request.getParameter("id");
        session.setAttribute("getOneOrderInfoId",str);
        System.out.println("transit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+str);
        return "redirect:/html/order.html";
    }
    @RequestMapping(value = "getOneOrderInfo")
    @ResponseBody   //得到一个订单的信息
    public JSONObject getOneOrderInfo( HttpServletRequest request, HttpServletResponse response,HttpSession session){
        String str1 = request.getParameter("qq");
        String str = (String) session.getAttribute("getOneOrderInfoId");
        int id = Integer.valueOf(str);
        System.out.println("得到一个订单的信息"+str1);
        Order order = orderDao.getOneOrder(id);
        Room room = roomDao.getRoomByRID(order.getO_room());
        Hotel hotel = hotelDao.getHotelById(room.getR_hotel());
        JSONObject object = new JSONObject();
        System.out.println(order.getO_name());
        object.put("o_Etime", String.valueOf(order.getO_Etime()));
        object.put("o_Stime", String.valueOf(order.getO_Stime()));
        object.put("o_account",order.getO_account());
        object.put("o_id", order.getO_id());
        object.put("o_name", order.getO_name());
        object.put("o_phone", order.getO_phone());
        object.put("o_price", order.getO_price());
        object.put("o_room",order.getO_room());
        object.put("o_time", String.valueOf(order.getO_time()));
        object.put("o_user", order.getO_user());
        object.put("state", order.getState());
        object.put("roomname", room.getR_name());
        object.put("rule", room.getR_rule());
        object.put("add1", hotel.getH_address());
      //  object.put("phone", hotel.get);
        return object;
    }

    @RequestMapping(value = "agreeOrderById")//同意用户订单
    public void  agreeOrderById( HttpServletRequest request, HttpServletResponse response){
        String str = request.getParameter("agreeOrderid");
        int id = Integer.valueOf(str);
        System.out.println("id:"+str);
        orderDao.updateOrderState(id,"已预订");
    }

    @RequestMapping(value = "querenOrderById")//用户订单完成
    public void  querenOrderById( HttpServletRequest request, HttpServletResponse response){
        String str = request.getParameter("querenorderid");
        int id = Integer.valueOf(str);
        System.out.println("id:"+str);
        orderDao.updateOrderState(id,"已完成");
    }
}
