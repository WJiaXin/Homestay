package com.example.demo.service;


import com.example.demo.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderService {
    abstract void addOrder(Order order);
    void  deleteOrderById(int id);
    List<Order> myOrder (int id);
    void updateOrder(Order order);
    int getOrderNum (int id);
    int getEndNum(@Param("id") int id,@Param("state") String state);
    int getAfterNum(@Param("id") int id,@Param("state") String state,@Param("state1") String state1);
    List<Map<String, Object>> getEndOrder(@Param("id") int id, @Param("state") String state,@Param("start") int start);
    List<Map<String, Object>> getAllOrder(@Param("id") int id,@Param("start") int start);
    List<Map<String, Object>> getBookingOrder(@Param("id") int id,@Param("state") String state,@Param("state1") String state1,@Param("start") int start);
    void updateOrderState(@Param("id") int id,@Param("state") String state);
    List<Map<String, Object>> getAllOrderForH(@Param("id") String id,@Param("start") int start);
    int getAllOrderForHNum(String id);
    List<Map<String, Object>> getOneTypeOrder(@Param("id") String id,@Param("state") String state,@Param("start") int start);
    int getOneTypeOrderNum(@Param("id") String id,@Param("state") String state);
    Order getOneOrder(int id);
}
