package com.example.orders.service;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.dto.OrderResponseListWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrdersService {

    ResponseEntity<String> post(OrderRequest orderRequest);

    ResponseEntity<OrderResponse> get(Long orderId);

    ResponseEntity<OrderResponseListWrapper> getAll();

    ResponseEntity<Void> delete(Long machineId);
}
