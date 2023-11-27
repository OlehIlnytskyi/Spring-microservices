package com.example.orders.service;

import com.example.orders.model.Order;
import com.example.orders.model.OrderRequest;
import com.example.orders.model.OrderResponse;

import java.util.List;

public interface OrdersService {

    void post(OrderRequest orderRequest);

    OrderResponse get(Long orderId);

    List<OrderResponse> getAll();

    void delete(Long machineId);
}
