package com.example.demo.dao;

import com.example.demo.entity.Homestay;
import com.example.demo.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HomestayDao { int setHomestay(Homestay homestay);
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
