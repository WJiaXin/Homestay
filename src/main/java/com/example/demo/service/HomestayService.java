package com.example.demo.service;

import com.example.demo.entity.Homestay;
import com.example.demo.entity.Room;

import java.util.List;

public interface HomestayService {
   int setHomestay(Homestay homestay);
   Homestay findHomestay(String userid,String time);
   int setRoom(Room room);
   Room findRoom(int hotelid,String time);
   int setRoomFacility(Room room);
   int setRoomImg(Room room);
   int setRoomPrice(Room room);
   List<Homestay> findIdHomestay(String userid);
   int setHstate(int H_id,String state);
   List<Room> findIdRoom(int Hid);
   List<Homestay> findAllHomestay();
}
