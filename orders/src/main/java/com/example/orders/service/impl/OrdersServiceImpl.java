package com.example.orders.service.impl;

import com.example.orders.model.*;
import com.example.orders.service.OrdersService;
import com.example.orders.service.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void post(OrderRequest orderRequest) {

        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream()
                .map(this::mapToBase)
                .toList();

        orderItems.forEach(orderItem -> orderItem.setPrice(
                restTemplate.getForObject("http://hangar/hangar/get?machineId=" + orderItem.getMachineId(), MachineResponse.class)
                        .getPrice()
        ));

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
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " is missing."));

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
