package com.example.clientinventory.controller;

import com.example.clientinventory.models.Catalog;
import com.example.clientinventory.models.Order;
import com.example.clientinventory.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/initValues")
    public String initValues() {
        return ordersService.initValues();
    }

    @GetMapping("/getAll")
    public Catalog<Order> getAll() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/get")
    public Optional<Order> getById(@RequestParam long id) {
        return ordersService.getById(id);
    }

    @PostMapping("/post")
    public void add(@RequestBody Order order) {
        ordersService.add(order);
    }

    @PutMapping("/put")
    public void edit(@RequestBody Order order) {
        ordersService.edit(order);
    }

    @DeleteMapping("/remove")
    public void removeById(@RequestParam long id) {
        ordersService.removeById(id);
    }
}
