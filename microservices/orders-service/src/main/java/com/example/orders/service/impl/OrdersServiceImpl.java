package com.example.orders.service.impl;

import com.example.orders.dto.MachineResponse;
import com.example.orders.dto.OrderItemRequest;
import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.model.*;
import com.example.orders.service.OrdersService;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.discovery.ServicesNames;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@Log
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "getMachinesById_cb", fallbackMethod = "postFallback")
    public ResponseEntity<String> post(OrderRequest orderRequest) {

        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream()
                .map(this::mapToBase)
                .toList();

        String url = "lb://" + ServicesNames.HANGAR_SERVICE + "/api/hangar/get?machineId=";

        try {
            orderItems.forEach(orderItem -> {
                orderItem.setPrice(
                        getMachineResponse(url, orderItem.getMachineId())
                                .getPrice()
                );
            });
        } catch (HttpClientErrorException e) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No Content");
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
                .body("Successfully created new Order");
    }

    private MachineResponse getMachineResponse(String url, Long machineId) {
        return restTemplate.getForEntity(url + machineId, MachineResponse.class)
                .getBody();
    }


    private ResponseEntity<String> postFallback(Exception e) {
        return ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT)
                .body("Oups, looks like something went wrong, please, give us some time to fix it :)");
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
