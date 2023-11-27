package com.example.orders.service.impl;

import com.example.orders.model.*;
import com.example.orders.service.OrdersService;
import com.example.orders.service.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public void post(OrderRequest orderRequest) {

        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream()
                .map(this::mapToBase)
                .toList();

        Order order = Order.builder()
                .customer_id(orderRequest.getCustomerId())
                .orderItems(orderItems)
                .total(orderItems.stream()
                        .map(OrderItem::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();

        ordersRepository.save(order);
    }

    @Override
    public OrderResponse get(Long orderId) {
        Order order = ordersRepository.findById(orderId)
                .orElseThrow();

        return OrderResponse.builder()
                .id(order.getId())
                .customer_id(order.getCustomer_id())
                .orderItems(order.getOrderItems())
                .total(order.getTotal())
                .build();
    }

    @Override
    public List<OrderResponse> getAll() {
        return ordersRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long machineId) {
        ordersRepository.deleteById(machineId);
    }


    private OrderItem mapToBase(OrderItemRequest orderItemRequest) {
        return OrderItem.builder()
                .machineId(orderItemRequest.getMachineId())
                .price(orderItemRequest.getPrice())
                .build();
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customer_id(order.getCustomer_id())
                .orderItems(order.getOrderItems())
                .total(order.getTotal())
                .build();
    }
}
