package com.example.demo.service;

import com.example.demo.dao.RoomDao;
import com.example.demo.entity.Room;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    RoomDao roomDao;

    @Override
    public Room getRoomByRID(int id){
        return  roomDao.getRoomByRID(id);
    }
    @Override
    public List<Room> getApplyRoom(@Param("start") int start){
        return  roomDao.getApplyRoom(start);
    }
    @Override
    public  void updataRoomState(@Param("id") int id, @Param("state") String state){
        roomDao.updataRoomState(id, state);
    }
    @Override
    public int getRoomApplyNum(){
        return roomDao.getRoomApplyNum();
    }
    @Override
    public int getMinRoomPrice(int id){
        return roomDao.getMinRoomPrice(id);
    }
    @Override
    public  List<Room> getRoomByHid(int id){
        return roomDao.getRoomByHid(id);
    }
}
