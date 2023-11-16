package com.example.clientinventory.service;

import com.example.clientinventory.models.Catalog;
import com.example.clientinventory.models.Order;
import com.example.clientinventory.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    private boolean hasInitValue;

    public String initValues() {
        if (hasInitValue) return "It has been already initialized!";

        ordersRepository.save(new Order(0, 25, 3, 25));
        ordersRepository.save(new Order(0, 7, 2, 50));
        ordersRepository.save(new Order(0, 12, 18, 10));

        hasInitValue = true;
        return "Successfully initialized!";
    }

    public Catalog<Order> getAllOrders() {
        return new Catalog<>(ordersRepository.findAll());
    }

    public Optional<Order> getById(long id) {
        return ordersRepository.findById(id);
    }

    public void add(Order order) {
        ordersRepository.save(order);
    }

    public void edit(Order order) {
        ordersRepository.save(order);
    }

    public void removeById(long id) {
        ordersRepository.deleteById(id);
    }
}
