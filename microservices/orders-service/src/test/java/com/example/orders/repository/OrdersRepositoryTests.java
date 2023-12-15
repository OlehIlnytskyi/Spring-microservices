package com.example.orders.repository;

import com.example.orders.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.example.orders.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrdersRepositoryTests {

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void testSaveOrder() {
        // Arrange
        Order order = getTestOrderA();

        // Act
        Order savedOrder = ordersRepository.save(order);

        // Assert
        assertNotNull(savedOrder);
        assertEquals(order.getCustomer_id(), savedOrder.getCustomer_id());
        assertEquals(order.getOrderItems(), savedOrder.getOrderItems());
        assertEquals(order.getTotal(), savedOrder.getTotal());
    }

    @Test
    void testSaveAllOrders() {
        // Arrange
        List<Order> orderList = List.of(getTestOrderA(), getTestOrderB());

        // Act
        List<Order> savedOrdersList = ordersRepository.saveAll(orderList);

        // Assert
        assertEquals(2, savedOrdersList.size());
        assertEquals(orderList.get(0).getOrderItems(), savedOrdersList.get(0).getOrderItems());
        assertEquals(orderList.get(1).getOrderItems(), savedOrdersList.get(1).getOrderItems());
    }

    @Test
    void testFindOrderById() {
        // Arrange
        Order order = getTestOrderA();
        Order savedOrder = ordersRepository.save(order);

        // Act
        Order foundOrder = ordersRepository.findById(savedOrder.getId())
                .orElseThrow();

        // Assert
        assertEquals(savedOrder.getId(), foundOrder.getId());
        assertEquals(savedOrder.getCustomer_id(), foundOrder.getCustomer_id());
        assertEquals(savedOrder.getOrderItems(), foundOrder.getOrderItems());
        assertEquals(savedOrder.getTotal(), foundOrder.getTotal());
    }

    @Test
    void testFindAllOrders() {
        // Arrange
        Order savedOrderA = ordersRepository.save(getTestOrderA());
        Order savedOrderB = ordersRepository.save(getTestOrderB());

        // Act
        List<Order> orderList = ordersRepository.findAll();

        // Asserts
        assertEquals(2, orderList.size());
        assertNotEquals(savedOrderA, savedOrderB);
        assertEquals(savedOrderA, orderList.get(0));
        assertEquals(savedOrderB, orderList.get(1));
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        Order savedOrder = ordersRepository.save(getTestOrderA());

        // Act
        ordersRepository.delete(savedOrder);
        Optional<Order> deletedOrderOpt = ordersRepository.findById(savedOrder.getId());

        // Assert
        assertTrue(deletedOrderOpt.isEmpty());
    }

    @Test
    void testDeleteOrderById() {
        // Arrange
        Order savedOrder = ordersRepository.save(getTestOrderA());

        // Act
        ordersRepository.deleteById(savedOrder.getId());
        Optional<Order> deletedOrderOpt = ordersRepository.findById(savedOrder.getId());

        // Assert
        assertTrue(deletedOrderOpt.isEmpty());
    }
}
