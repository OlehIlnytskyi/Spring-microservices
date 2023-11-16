package com.example.militarymachineshangar.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class Catalog<T> {

    private Map<T, Integer> map;
}
