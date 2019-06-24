package com.example.demo.controller;


import com.example.demo.entity.Gaode;
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
import java.util.*;

@RequestMapping(value = "/homestay")
@Controller
public class HomestayController {
    @Autowired
    HomestayService homestayService;
    private Gaode gaode=new Gaode();

    @RequestMapping(value="/setHomestay")                //获取指定地点民宿信息
    public void setHomestay(HttpServletResponse response,HttpServletRequest request, @RequestBody JSONObject json){
        System.out.println("城市："+json.get("city"));
        String city[]=json.get("city").toString().split("/");
        List<Homestay> homestayList=new ArrayList<>();
        if(city.length==3){
            homestayList=homestayService.findDistrHomestay(city[2]);
            System.out.println(city[1] + "+" + city[2]);
        }else{
            homestayList=homestayService.findCityHomestay(city[1]);
        }
        request.getSession().setAttribute("city",json.get("city"));
        request.getSession().setAttribute("daterangeC",json.get("daterangeC"));
        request.getSession().setAttribute("homestayList",homestayList);
        request.getSession().setAttribute("homestayPage",new ArrayList<>(homestayList));
    }
    @RequestMapping(value="/toHomestay")
    public String toHomestay(HttpServletRequest request){
     return "findHomestay";
    }
    @RequestMapping(value="/pageHomestay")
    @ResponseBody
    public  List<Homestay> pageHomestay(HttpServletRequest request,@RequestBody JSONObject json){
        List<Homestay> homestayList= (List<Homestay>) request.getSession().getAttribute("homestayPage");
        List<Homestay> homestayPageList=new ArrayList<>();
        int page=(Integer.parseInt(json.get("page").toString())-1)*8;

       System.out.println("坐标:"+json.get("address"));
       for(int i=page;i<page+8&&i<homestayList.size();i++){
        homestayList.get(i).setDistance(gaode.getDistance(JSONObject.fromObject(homestayList.get(i).getH_address()).get("address").toString(),json.get("address").toString()));
        homestayPageList.add(homestayList.get(i));
    }
        return homestayPageList;
    }
    @RequestMapping(value="/getHomestay")                //返回民宿信息数据
    @ResponseBody
    public JSONArray getHomestay(HttpServletRequest request,@RequestBody JSONObject json){
        List<Homestay> homestayList= (List<Homestay>) request.getSession().getAttribute("homestayList");
        List<Homestay> homestayPageList=new ArrayList<>();
        JSONArray jsonArray=new JSONArray();
        int page=(Integer.parseInt(json.get("page").toString())-1)*8;
        int pageAll;
        if(homestayList.size()%8==0){
            pageAll=homestayList.size()/8;
        }else{
            pageAll=homestayList.size()/8+1;
        }
        System.out.println("坐标:"+json.get("address"));
        for(int i=page;i<page+8&&i<homestayList.size();i++){
            homestayList.get(i).setDistance(gaode.getDistance(JSONObject.fromObject(homestayList.get(i).getH_address()).get("address").toString(),json.get("address").toString()));
            homestayPageList.add(homestayList.get(i));
        }
        jsonArray.add(0,pageAll);
        jsonArray.add(1,homestayPageList);
        jsonArray.add(2,request.getSession().getAttribute("city"));
        jsonArray.add(3,request.getSession().getAttribute("daterangeC"));
        System.out.println("Jinrua------------");
        return jsonArray;
    }
    @RequestMapping(value="/getNearHomestay")              //按距离排序
    @ResponseBody
    public void getNearHomestay(HttpServletRequest request, @RequestBody JSONObject json){
        List<Homestay> homestayPage= (List<Homestay>) request.getSession().getAttribute("homestayPage");
        for(int i=0;i<homestayPage.size();i++){
            String distance=gaode.getDistance(JSONObject.fromObject(homestayPage.get(i).getH_address()).get("address").toString(),json.get("address").toString());
            homestayPage.get(i).setDistance(distance);
            homestayPage.get(i).setDistanceN(Integer.parseInt(distance));
        }
        //实现Comparator进行排序
        Collections.sort(homestayPage,new Comparator<Object>(){

            public int compare(Object o1, Object o2) {
                return  ((Homestay) o1).getDistanceN()-((Homestay) o2).getDistanceN();
            }
        });
      request.getSession().setAttribute("homestayPage",homestayPage);
    }
    @RequestMapping(value="/getPriceHomestay")       //按价格排序
    @ResponseBody
    public void getPriceHomestay(HttpServletRequest request, @RequestBody JSONObject json){
        List<Homestay> homestayList= (List<Homestay>) request.getSession().getAttribute("homestayPage");
        //实现Comparator进行排序
        Collections.sort(homestayList,new Comparator<Object>(){

            public int compare(Object o1, Object o2) {
                return  ((Homestay) o1).getPrice()-((Homestay) o2).getPrice();
            }
        });
        request.getSession().setAttribute("homestayPage",homestayList);
    }
    @RequestMapping(value="/getGoodHomestay")     //按评分排序
    @ResponseBody
    public void getGoodHomestay(HttpServletRequest request, @RequestBody JSONObject json){
        List<Homestay> homestayList= (List<Homestay>) request.getSession().getAttribute("homestayPage");
        //实现Comparator进行排序
        Collections.sort(homestayList,new Comparator<Object>(){

            public int compare(Object o1, Object o2) {
                return (int) (((Homestay) o2).getH_allscore()*10-((Homestay) o1).getH_allscore()*10);
            }
        });
        request.getSession().setAttribute("homestayPage",homestayList);
    }


    @RequestMapping(value="/lookHomestay")
    @ResponseBody
    public  JSONArray lookHomestay( HttpServletRequest request){
        Homestay homestay=homestayService.findHidHomestayS(Integer.parseInt(request.getSession().getAttribute("homestayid").toString()));
        String daterange[]=request.getSession().getAttribute("daterangeC").toString().split(" 至 ");
        for(int i=0;i<homestay.getRoom().size();i++){
              if(homestayService.isFull(daterange[0],daterange[1],homestay.getRoom().get(i).getR_id())==null){
                  homestay.getRoom().get(i).setFull("预定");

              }else{
                  homestay.getRoom().get(i).setFull("满房");
              }
        }
       JSONArray jsonArray=new JSONArray();
        request.getSession().setAttribute("Hroom",homestay.getRoom());
        request.getSession().setAttribute("HomestayShow",homestay);
       jsonArray.add(0,request.getSession().getAttribute("daterangeC"));
       jsonArray.add(1, request.getSession().getAttribute("city"));
       jsonArray.add(2,request.getSession().getAttribute("address"));
       jsonArray.add(3,homestay);
       return  jsonArray;
    }
    @RequestMapping(value="/isFull")
    @ResponseBody
    public  List<Room> isFull( HttpServletRequest request,@RequestParam("daterangeC") String daterangeC){
        List<Room> roomList= (List<Room>) request.getSession().getAttribute("Hroom");
        System.out.println(daterangeC);
        String daterange[]=daterangeC.split(" 至 ");
        for(int i=0;i<roomList.size();i++){
            if(homestayService.isFull(daterange[0],daterange[1],roomList.get(i).getR_id())==null){
                roomList.get(i).setFull("预定");

            }else{
                roomList.get(i).setFull("满房");
            }
        }
       request.getSession().setAttribute("daterangeC",daterangeC);
        return  roomList;
    }
    @RequestMapping(value="/Homestay")
    public String Homestay( HttpServletRequest request,@RequestParam("hid") String hid,@RequestParam("address") String address){
        request.getSession().setAttribute("homestayid",hid);
        request.getSession().setAttribute("address",address);
        return  "homestay";
    }
    @RequestMapping(value="/lookRoom")
    @ResponseBody
    public  JSONArray lookRoom( HttpServletRequest request){
        int rid= Integer.parseInt(request.getSession().getAttribute("rid").toString());
        Homestay homestay= (Homestay) request.getSession().getAttribute("HomestayShow");
        Room room = new Room();
      for(int i=0;i<homestay.getRoom().size();i++){
          if(homestay.getRoom().get(i).getR_id()==rid){
              room=homestay.getRoom().get(i);
              break;
          }
      }
      JSONArray jsonArray=new JSONArray();
        jsonArray.add(0,request.getSession().getAttribute("daterangeC"));
        jsonArray.add(1,room);
        jsonArray.add(2,homestay.getUser());
        jsonArray.add(3,homestay.getH_name());
        return  jsonArray;
    }
    @RequestMapping(value="/Room")
    public String Room( HttpServletRequest request,@RequestParam("rid") int rid){
        request.getSession().setAttribute("rid",rid);
        return  "room";
    }





    @RequestMapping(value="/step1")          //添加民宿第一步
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
    @RequestMapping(value="/step2") //添加民宿第二步
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

    @RequestMapping(value="/step3")                      //添加民宿第三步
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
    @RequestMapping(value="/step4")                //添加民宿第四步
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
    @RequestMapping(value="/step5")                  //发布民宿
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
    @RequestMapping(value="/toPersonal")
    public String toPersonal(HttpServletResponse response, HttpServletRequest request) throws IOException {
        return "personal";
    }
    @RequestMapping(value="/getUHomestay")
    @ResponseBody
    public List<Homestay> getUHomestay(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<Homestay> homestayList=homestayService.findIdHomestay("17738505200");
        System.out.println(this.getClass().getClassLoader().getResource("").getPath());
        return homestayList;
    }
    @RequestMapping(value="/setHRoom")
    public String setHRoom( HttpServletRequest request,@RequestParam("hid") int hid) throws IOException {
        request.getSession().setAttribute("hid",hid);
        return "Uhomestay";
    }
    @RequestMapping(value="/getHRoom")
    @ResponseBody
    public Homestay getHRoom(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Homestay homestay=homestayService.findHidHomestay((Integer) request.getSession().getAttribute("hid"));
        System.out.println("------------价格:"+homestay.getRoom().get(0).getR_name());
        return homestay;
    }

    @RequestMapping(value="/home")
    @ResponseBody
    public List<Homestay> home(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<Homestay> homestayList=homestayService.findAllHomestay();
        System.out.println("------------价格:"+homestayList.get(0).getH_name());
        return homestayList;
    }
    @RequestMapping(value="/Rshangjia")
    @ResponseBody
    public void Rshangjia(HttpServletRequest request,@RequestParam("rid") int rid){
       homestayService.setRoomState(rid,"已上线");
    }
    @RequestMapping(value="/Rxiajia")
    @ResponseBody
    public void Rxiajia(HttpServletRequest request,@RequestParam("rid") int rid){
homestayService.setRoomState(rid,"已下线");
    }
    @RequestMapping(value="/Rshanchu")
    @ResponseBody
    public void Rshanchu(HttpServletRequest request,@RequestParam("rid") int rid){
   homestayService.deleteRoom(rid);
    }
    @RequestMapping(value="/Hshangjia")
    public String Hshangjia(@RequestParam("hid") int hid){
        System.out.println(hid);
        homestayService.setHstate(hid,"已上线");
        return "personal";
    }
    @RequestMapping(value="/Hxiajia")
    public String Hxiajia(@RequestParam("hid") int hid){
        System.out.println(hid);
        homestayService.setHstate(hid,"已下线");
        return "personal";
    }
    @RequestMapping(value="/Hshanchu")
    public String Hshanchu(@RequestParam("hid") int hid){
        System.out.println(hid);
        homestayService.deleteHomestay(hid);
        return "personal";
    }
    @RequestMapping(value="/test")
    public void test(HttpServletResponse response, HttpServletRequest request){
        System.out.println("进入");

    }



}
