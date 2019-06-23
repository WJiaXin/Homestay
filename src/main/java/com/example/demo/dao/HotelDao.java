package com.example.demo.dao;

import com.example.demo.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HotelDao {
    Hotel getHotelById(int id);
}
