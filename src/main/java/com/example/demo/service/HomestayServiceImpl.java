package com.example.demo.service;

import com.example.demo.dao.HomestayDao;
import com.example.demo.entity.Homestay;
import com.example.demo.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomestayServiceImpl implements HomestayService{
    @Autowired
    HomestayDao homestayDao;

    @Override
    public int setHomestay(Homestay homestay){
        return homestayDao.setHomestay(homestay);
    }
    @Override
    public Homestay findHomestay(String userid,String time){
        return  homestayDao.findHomestay(userid,time);
    }
    @Override
    public int setRoom(Room room){ return homestayDao.setRoom(room); }
    @Override
    public Room findRoom(int hotelid, String time){ return  homestayDao.findRoom(hotelid,time); }
    @Override
    public  int setRoomFacility(Room room){
        return homestayDao.setRoomFacility(room);
    }
    @Override
    public int setRoomImg(Room room){
        return homestayDao.setRoomImg(room);
    }
    @Override
    public int setRoomPrice(Room room){
        return homestayDao.setRoomPrice(room);
    }
    @Override
    public List<Homestay> findIdHomestay(String userid){
        return homestayDao.findIdHomestay(userid);
    }
    @Override
    public int setHstate(int H_id,String state){
        return homestayDao.setHstate(H_id,state);
    }
    @Override
   public  List<Room> findIdRoom(int Hid){
        return homestayDao.findIdRoom(Hid);
    }
    @Override
    public List<Homestay> findAllHomestay(){
        return homestayDao.findAllHomestay();
    }
}
