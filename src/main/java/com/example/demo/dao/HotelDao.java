package com.example.demo.dao;

import com.example.demo.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelDao {
    Hotel getHotelById(int id);
    List<Hotel> getApplyHotel(@Param("start") int start);
    void updataHotelState(@Param("id") int id, @Param("state") String state);
    int getHotelApplyNum();
}
