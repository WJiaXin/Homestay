package com.example.demo.service;

import com.example.demo.dao.HotelDao;
import com.example.demo.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    HotelDao hotelDao;
    @Override
    public Hotel getHotelById(int id){
        return  hotelDao.getHotelById(id);
    }
}
