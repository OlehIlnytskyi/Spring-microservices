package com.example.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseListWrapper {

    private List<OrderResponse> orderResponseList;

    public static OrderResponseListWrapper of(List<OrderResponse> orderResponseList) {
        return new OrderResponseListWrapper(orderResponseList);
    }
}
