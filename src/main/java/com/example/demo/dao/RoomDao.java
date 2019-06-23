package com.example.demo.dao;

import com.example.demo.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoomDao {
    Room getRoomByRID(int id);
}
