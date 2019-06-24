package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import  com.example.demo.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface OrderDao {
     void addOrder(Order order);
     void deleteOrderById(int id);
     List<Order> myOrder (int id);
     void updateOrder(Order order);
     int getOrderNum (@Param("id") int id);
     int getEndNum(@Param("id") int id,@Param("state") String state);
     int getAfterNum(@Param("id") int id,@Param("state") String state,@Param("state1") String state1);
     List<Map<String, Object>> getEndOrder(@Param("id") int id,@Param("state") String state,@Param("start") int start);    //结束
     List<Map<String, Object>> getAllOrder(@Param("id") int id,@Param("start") int start);                                 //全部
     List<Map<String, Object>> getBookingOrder(@Param("id") int id,@Param("state") String state,@Param("state1") String state1,@Param("start") int start);//两个
     void updateOrderState(@Param("id") int id,@Param("state") String state);//取消
     List<Map<String, Object>> getAllOrderForH(@Param("id") String id,@Param("start") int start);
     int getAllOrderForHNum(String id);
     List<Map<String, Object>> getOneTypeOrder(@Param("id") String id,@Param("state") String state,@Param("start") int start);
     int getOneTypeOrderNum(@Param("id") String id,@Param("state") String state);
     Order getOneOrder(int id);

}
