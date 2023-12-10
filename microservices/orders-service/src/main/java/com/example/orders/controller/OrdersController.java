package com.example.orders.controller;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody OrderRequest orderRequest) {
        return ordersService.post(orderRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<OrderResponse> get(@RequestParam Long orderId) {
        return ordersService.get(orderId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ordersService.getAll();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long orderId) {
        return ordersService.delete(orderId);
    }
}
