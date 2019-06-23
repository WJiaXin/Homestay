package com.example.demo.service;

import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    @Override
    public void deleteOrderById(int id) {
        orderDao.deleteOrderById(id);
    }

    @Override
    public List<Order> myOrder(int id) {
        return orderDao.myOrder(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Map<String, Object>> getEndOrder(@Param("id") int id, @Param("state") String state,@Param("start") int start) {
        return orderDao.getEndOrder(id, state,start);
    }

    @Override
    public List<Map<String, Object>> getAllOrder(@Param("id") int id, @Param("start") int start) {
        return orderDao.getAllOrder(id, start);
    }

    @Override
    public List<Map<String, Object>> getBookingOrder(@Param("id") int id, @Param("state") String state, @Param("state1") String state1,@Param("start") int start) {
        return orderDao.getBookingOrder(id, state, state1,start);
    }

    @Override
    public int getOrderNum(int id) {
        return orderDao.getOrderNum(id);
    }
    @Override
    public int getEndNum(@Param("id") int id,@Param("state") String state){
        return orderDao.getEndNum(id,state);
    }
    @Override
    public int getAfterNum(@Param("id") int id,@Param("state") String state,@Param("state1") String state1){
        return  orderDao.getAfterNum(id, state, state1);
    }
    @Override
      public void updateOrderState(@Param("id") int id,@Param("state") String state){
      orderDao.updateOrderState(id,state);
    }
    @Override
        public List<Map<String, Object>> getAllOrderForH(@Param("id") int id,@Param("start") int start){
        return orderDao.getAllOrderForH(id, start);
    }
    @Override
        public int getAllOrderForHNum(int id){
            return orderDao.getAllOrderForHNum(id);
    }
    @Override
    public List<Map<String, Object>> getOneTypeOrder(@Param("id") int id,@Param("state") String state,@Param("start") int start){
        return orderDao.getOneTypeOrder(id, state, start);
    }
    @Override
    public int getOneTypeOrderNum(@Param("id") int id,@Param("state") String state){
        return  orderDao.getOneTypeOrderNum(id, state);
    }
    @Override
    public Order getOneOrder(int id){
        return  orderDao.getOneOrder(id);
    }
}
