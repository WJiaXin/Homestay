package com.example.demo.dao;

import com.example.demo.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomDao {
    Room getRoomByRID(int id);
    List<Room> getApplyRoom(@Param("start") int start);
    void updataRoomState(@Param("id") int id, @Param("state") String state);
    int getRoomApplyNum();
    int getMinRoomPrice(int id);
    List<Room> getRoomByHid(int id);
}
