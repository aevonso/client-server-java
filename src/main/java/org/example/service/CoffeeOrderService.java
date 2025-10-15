package org.example.service;

import org.example.model.CoffeeOrder;
import org.example.model.dto.CoffeeOrderRequest;

import java.util.List;

public interface CoffeeOrderService {
    CoffeeOrder createCoffeeOrder(CoffeeOrderRequest request);
    List<CoffeeOrder> getAllCoffeeOrders();
    CoffeeOrder updateCoffeeOrder(Long orderId, CoffeeOrderRequest request);
}
