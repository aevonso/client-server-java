package org.example.service;

import org.example.model.DessertOrder;
import org.example.model.dto.DessertOrderRequest;

import java.util.List;

public interface DessertOrderService {
    DessertOrder createDessertOrder(DessertOrderRequest request);
    List<DessertOrder> getAllDessertOrders();
    DessertOrder updateDessertOrder(Long orderId, DessertOrderRequest request);
    void deleteDessertOrder(Long orderId);
    List<DessertOrder> getOrdersByDessert(Long dessertId);
    List<DessertOrder> getOrdersByWaiter(String waiterName);
    List<DessertOrder> getOrdersByCustomer(String customerName);
    List<DessertOrder> getOrdersByCustomerPhone(String customerPhone);
    void deleteOrdersByDessert(Long dessertId);
}
