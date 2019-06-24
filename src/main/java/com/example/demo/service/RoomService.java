package com.example.demo.service;

import com.example.demo.entity.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomService {
    Room getRoomByRID(int id);
    List<Room> getApplyRoom(@Param("start") int start);
    void updataRoomState(@Param("id") int id, @Param("state") String state);
    int getRoomApplyNum();
    int getMinRoomPrice(int id);
    List<Room> getRoomByHid(int id);
}
