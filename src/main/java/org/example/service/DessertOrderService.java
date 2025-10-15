package org.example.service;

import org.example.model.DessertOrder;
import org.example.model.dto.DessertOrderRequest;

import java.util.List;

public interface DessertOrderService {
    DessertOrder createDessertOrder(DessertOrderRequest request);
    List<DessertOrder> getAllDessertOrders();
    DessertOrder updateDessertOrder(Long orderId, DessertOrderRequest request);
}
