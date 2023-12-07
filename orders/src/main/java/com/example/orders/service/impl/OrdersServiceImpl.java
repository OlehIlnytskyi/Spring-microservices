package com.example.orders.service.impl;

import com.example.orders.dto.MachineResponse;
import com.example.orders.dto.OrderItemRequest;
import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.model.*;
import com.example.orders.service.OrdersService;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.discovery.ServicesNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
    public ResponseEntity<Void> post(OrderRequest orderRequest) {

        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream()
                .map(this::mapToBase)
                .toList();

        String url = "lb://" + ServicesNames.HANGAR_SERVICE + "/api/hangar/get?machineId=";

        try {
            orderItems.forEach(orderItem -> orderItem.setPrice(
                    restTemplate.getForEntity(
                                    url + orderItem.getMachineId(), MachineResponse.class)
                            .getBody()
                            .getPrice()
            ));
        } catch (HttpClientErrorException e) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

        Order order = Order.builder()
                .customer_id(orderRequest.getCustomerId())
                .orderItems(orderItems)
                .total(orderItems.stream()
                        .map(OrderItem::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();

        ordersRepository.save(order);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseEntity<OrderResponse> get(Long orderId) {
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " is missing."));

        OrderResponse body = OrderResponse.builder()
                .id(order.getId())
                .customer_id(order.getCustomer_id())
                .orderItems(order.getOrderItems())
                .total(order.getTotal())
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @Override
    public ResponseEntity<List<OrderResponse>> getAll() {
        List<OrderResponse> body = ordersRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @Override
    public ResponseEntity<Void> delete(Long machineId) {
        ordersRepository.deleteById(machineId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
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
