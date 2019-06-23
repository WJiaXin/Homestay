package com.example.demo.service;

import com.example.demo.dao.RoomDao;
import com.example.demo.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    RoomDao roomDao;

    @Override
    public Room getRoomByRID(int id){
        return  roomDao.getRoomByRID(id);
    }
}
