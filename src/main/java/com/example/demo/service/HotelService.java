package com.example.demo.service;

import com.example.demo.entity.Hotel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HotelService {
    Hotel getHotelById(int id);
    List<Hotel> getApplyHotel(@Param("start") int start);
    void updataHotelState(@Param("id") int id, @Param("state") String state);
    int getHotelApplyNum();
}
