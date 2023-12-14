package com.example.orders.service.impl;

import com.example.orders.communication.OrdersCommunicator;
import com.example.orders.dto.*;
import com.example.orders.model.*;
import com.example.orders.service.OrdersService;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.utils.MyMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Log
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersCommunicator ordersCommunicator;

    @Override
    public ResponseEntity<String> post(OrderRequest orderRequest) {

        List<OrderItem> orderItems;

        try {
            orderItems = getOrderItems(orderRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No Content");
        }

        Order order = MyMapper.mapToBase(orderRequest, orderItems);

        ordersRepository.save(order);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully created new Order");
    }

    private ResponseEntity<String> postFallback(Exception e) {
        log.info("Exception in post method - " +  e.getMessage());
        return ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT)
                .body("Oops, looks like something went wrong, please, give us some time to fix it :)");
    }


    @Override
    public ResponseEntity<OrderResponse> get(Long orderId) {
        OrderResponse body = ordersRepository.findById(orderId)
                .map(MyMapper::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " is missing."));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }


    @Override
    public ResponseEntity<List<OrderResponse>> getAll() {
        List<OrderResponse> body = ordersRepository.findAll().stream()
                .map(MyMapper::mapToResponse)
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


    private List<OrderItem> getOrderItems(OrderRequest orderRequest) {
        List<MachineResponse> machines = ordersCommunicator.getAllMachinesInWrapper()
                .getMachineResponses();

        return orderRequest.getOrderItemRequests().stream()
                .map(MyMapper::mapToBase)
                .peek(orderItem -> orderItem.setPrice(extractPriceFromMachines(orderItem.getMachineId(), machines)))
                .toList();
    }

    private BigDecimal extractPriceFromMachines(Long machineId, List<MachineResponse> machines) throws IllegalArgumentException {
        for (MachineResponse machine : machines) {
            if (Objects.equals(machineId, machine.getId())) {
                return machine.getPrice();
            }
        }

        throw new IllegalArgumentException("There is no machine with id " + machineId);
    }

}
