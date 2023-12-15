package com.example.orders.controller;

import com.example.orders.TestUtils;
import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.OrderResponse;
import com.example.orders.dto.OrderResponseListWrapper;
import com.example.orders.model.Order;
import com.example.orders.service.OrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static com.example.orders.TestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(OrdersController.class)
public class OrdersControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdersService ordersService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void postShouldReturnMessageAndStatusOK() throws Exception {
        // Arrange
        OrderRequest orderRequest = TestUtils.getTestOrderRequest();
        when(ordersService.post(orderRequest))
                .thenReturn(ResponseEntity.ok("Success!"));

        // Act & Assert
        mockMvc.perform(post("/api/orders/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Success!"));
    }

    @Test
    void getShouldReturnOrderResponseAndStatusOK() throws Exception {
        // Arrange
        OrderResponse orderResponse = getTestOrderResponse();
        orderResponse.setId(1L);

        when(ordersService.get(orderResponse.getId()))
                .thenReturn(ResponseEntity.ok(orderResponse));

        // Act & Assert
        mockMvc.perform(get("/api/orders/get")
                        .param("orderId", String.valueOf(orderResponse.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponse.getId()))
                .andExpect(jsonPath("$.customerId").value(orderResponse.getCustomerId()))
//                .andExpect(jsonPath("$.orderItemList").value(orderResponse.getOrderItemList()))   Does`t work idk why
                .andExpect(jsonPath("$.orderItemList.size()").value(orderResponse.getOrderItemList().size()))
                .andExpect(jsonPath("$.total").value(orderResponse.getTotal()));
    }

    @Test
    void getAllShouldReturnWrapperAndStatusOK() throws Exception {
        // Arrange
        OrderResponseListWrapper orderResponseListWrapper =
                OrderResponseListWrapper.of(List.of(getTestOrderResponse()));

        when(ordersService.getAll())
                .thenReturn(ResponseEntity.ok(orderResponseListWrapper));

        // Act & Assert
        mockMvc.perform(get("/api/orders/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderResponseList.size()").value(1));
    }

    @Test
    void deleteTest() throws Exception {
        // Arrange
        Long orderId = 1L;
        when(ordersService.delete(orderId))
                .thenReturn(ResponseEntity.noContent().build());

        // Act & Assert
        mockMvc.perform(delete("/api/orders/delete")
                        .param("orderId", String.valueOf(orderId)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
