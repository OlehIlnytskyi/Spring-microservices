package com.example.orders.controller;

import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody OrderRequest orderRequest) {
        ordersService.post(orderRequest);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.FOUND)
    public OrderResponse get(@RequestParam Long orderId) {
        return ordersService.get(orderId);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAll() {
        return ordersService.getAll();
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@RequestParam Long orderId) {
        ordersService.delete(orderId);
    }
}
