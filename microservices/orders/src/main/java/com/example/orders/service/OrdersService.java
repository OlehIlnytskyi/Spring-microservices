package com.example.orders.service;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrdersService {

    ResponseEntity<Void> post(OrderRequest orderRequest);

    ResponseEntity<OrderResponse> get(Long orderId);

    ResponseEntity<List<OrderResponse>> getAll();

    ResponseEntity<Void> delete(Long machineId);
}
