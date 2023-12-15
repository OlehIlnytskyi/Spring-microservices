package com.example.orders;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.model.Order;
import com.example.orders.model.OrderItem;
import com.example.orders.utils.OrderMapper;

import java.math.BigDecimal;
import java.util.List;

public class TestUtils {

    // Order

    public static Order getTestOrderA() {
        return Order.builder()
                .customer_id(1L)
                .orderItems(List.of(
                        getOrderItemA(),
                        getOrderItemB()
                ))
                .total(BigDecimal.valueOf(6_000))
                .build();
    }

    public static Order getTestOrderB() {
        return Order.builder()
                .customer_id(1L)
                .orderItems(List.of(
                        getOrderItemB(),
                        getOrderItemC()
                ))
                .total(BigDecimal.valueOf(17_500))
                .build();
    }

    public static OrderItem getOrderItemA() {
        return OrderItem.builder()
                .machineId(1L)
                .price(BigDecimal.valueOf(1_000))
                .build();
    }

    public static OrderItem getOrderItemB() {
        return OrderItem.builder()
                .machineId(2L)
                .price(BigDecimal.valueOf(5_000))
                .build();
    }

    public static OrderItem getOrderItemC() {
        return OrderItem.builder()
                .machineId(3L)
                .price(BigDecimal.valueOf(12_500))
                .build();
    }


    // OrderRequest

    public static OrderRequest getTestOrderRequest() {
        return OrderRequest.builder()
                .customerId(1L)
                .orderItemRequestList(List.of())
                .build();
    }

    // OrderResponse

    public static OrderResponse getTestOrderResponse() {
        return OrderMapper.mapToResponse(getTestOrderA());
    }
}
