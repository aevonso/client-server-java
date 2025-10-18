package org.example.service;

import org.example.model.CoffeeOrder;
import org.example.model.dto.CoffeeOrderRequest;

import java.util.List;

public interface CoffeeOrderService {
    CoffeeOrder createCoffeeOrder(CoffeeOrderRequest request);
    List<CoffeeOrder> getAllCoffeeOrders();
    CoffeeOrder updateCoffeeOrder(Long orderId, CoffeeOrderRequest request);
    void deleteCoffeeOrder(Long orderId);
    List<CoffeeOrder> getOrdersByWaiter(String waiterName);
    List<CoffeeOrder> getOrdersByCustomer(String customerName);
    List<CoffeeOrder> getOrdersByCustomerPhone(String customerPhone);
}
