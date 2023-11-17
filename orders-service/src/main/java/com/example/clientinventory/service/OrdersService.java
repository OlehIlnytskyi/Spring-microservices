package com.example.clientinventory.service;

import com.example.clientinventory.models.Catalog;
import com.example.clientinventory.models.Machine;
import com.example.clientinventory.models.Order;
import com.example.clientinventory.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private RestTemplate restTemplate;

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

        // Action: get requested Machine from Hangar
        String url = "http://military-machines-hangar/hangar/get?id=" + order.getMachineId();
        Machine hangarMachine = restTemplate.getForObject(url, Machine.class);

        // Check: not null
        if (hangarMachine == null) {
            throw new IllegalArgumentException("There is no machine with id " + order.getMachineId() + "in hangar!");
        }

        // Check: hangar has enough to order
        if (hangarMachine.getCount() < order.getMachinesCount()){
            throw new IllegalArgumentException("There is not enough machines in hangar!");
        }

        // Action: removing machines from Hangar
        restTemplate.delete("http://military-machines-hangar/hangar/remove?id=" + order.getMachineId() +
                "&count=" + order.getMachinesCount());

        // Result: saving Order
        ordersRepository.save(order);
    }

    public void edit(Order order) {

        // Check: Order with same id is present
        ordersRepository.findById(order.getId())
                .orElseThrow();

        // Cancel order

        // Create new order
//        add(order);

        // Editing Order (REMOVE THIS)
        ordersRepository.save(order);
    }

    public void removeById(long id) {
        ordersRepository.deleteById(id);
    }
}
