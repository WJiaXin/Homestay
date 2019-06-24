package com.example.demo.service;

import com.example.demo.dao.HotelDao;
import com.example.demo.entity.Hotel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    HotelDao hotelDao;
    @Override
    public Hotel getHotelById(int id){
        return  hotelDao.getHotelById(id);
    }
    @Override
    public List<Hotel> getApplyHotel(@Param("start") int start){
        return hotelDao.getApplyHotel(start);
    }
    @Override
    public void updataHotelState(@Param("id") int id, @Param("state") String state){
        hotelDao.updataHotelState(id, state);
    }
    @Override
    public int getHotelApplyNum(){
        return hotelDao.getHotelApplyNum();
    }
}
