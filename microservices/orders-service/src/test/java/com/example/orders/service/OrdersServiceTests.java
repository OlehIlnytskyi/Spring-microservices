package com.example.orders.service;

import com.example.orders.communication.OrdersCommunicator;
import com.example.orders.dto.MachineResponseListWrapper;
import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.dto.OrderResponseListWrapper;
import com.example.orders.model.Order;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.impl.OrdersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static com.example.orders.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.example.orders.utils.OrderMapper.*;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTests {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrdersCommunicator ordersCommunicator;

    @InjectMocks
    private OrdersService ordersService = new OrdersServiceImpl();

    @Test
    void postShouldSaveOrderAndReturnOK() {
        // Arrange
        OrderRequest orderRequest = getTestOrderRequest();

        when(ordersCommunicator.getAllMachinesFromHangar())
                .thenReturn(MachineResponseListWrapper.of(List.of()));

        // Act
        ResponseEntity<String> result = ordersService.post(orderRequest);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(ordersRepository, times(1)).save(any());
    }

    @Test
    void getShouldReturnOrderResponseAndStatusOK() {
        // Arrange
        Order order = getTestOrderA();
        when(ordersRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        // Act
        ResponseEntity<OrderResponse> result = ordersService.get(order.getId());

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
        assertEquals(mapToResponse(order), result.getBody());
    }

    @Test
    void getAllShouldReturnWrapperAndStatusOK() {
        // Arrange
        List<Order> orderList = List.of(getTestOrderA(), getTestOrderB());
        when(ordersRepository.findAll())
                .thenReturn(orderList);

        // Act
        ResponseEntity<OrderResponseListWrapper> result = ordersService.getAll();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.hasBody());
    }

    @Test
    void deleteShouldDeleteOrderAndReturnStatusOK() {
        // Arrange
        Long orderId = 1L;

        // Act
        ResponseEntity<Void> result = ordersService.delete(orderId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(ordersRepository, times(1)).deleteById(orderId);
    }
}
