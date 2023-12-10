package com.example.orders.utils;

import com.example.orders.dto.OrderItemRequest;
import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.model.Order;
import com.example.orders.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class MyMapper {

    public static Order mapToBase(OrderRequest orderRequest, List<OrderItem> orderItems) {
        return Order.builder()
                .customer_id(orderRequest.getCustomerId())
                .orderItems(orderItems)
                .total(orderItems.stream()
                        .map(OrderItem::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }

    public static OrderItem mapToBase(OrderItemRequest orderItemRequest) {
        return OrderItem.builder()
                .machineId(orderItemRequest.getMachineId())
                .build();
    }

    public static OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customer_id(order.getCustomer_id())
                .orderItems(order.getOrderItems())
                .total(order.getTotal())
                .build();
    }

}
