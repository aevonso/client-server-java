package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.Dessert;
import org.example.model.DessertOrder;
import org.example.model.dto.DessertOrderRequest;
import org.example.repository.DessertOrderRepository;
import org.example.repository.DessertRepository;
import org.example.service.DessertOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DessertOrderServiceImpl implements DessertOrderService {

    private final DessertOrderRepository dessertOrderRepository;
    private final DessertRepository dessertRepository;

    @Override
    @Transactional
    public DessertOrder createDessertOrder(DessertOrderRequest request) {
        Dessert dessert = dessertRepository.findById(request.getDessertId())
                .orElseThrow(() -> new RuntimeException("Dessert not found"));

        if (Boolean.FALSE.equals(dessert.getIsAvailable())) {
            throw new RuntimeException("Dessert is not available");
        }

        DessertOrder order = new DessertOrder();
        order.setDessert(dessert);
        order.setQuantity(request.getQuantity());
        order.setCustomerName(request.getCustomerName());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(dessert.getPrice().multiply(
                java.math.BigDecimal.valueOf(request.getQuantity())));
        order.setStatus("PENDING");

        return dessertOrderRepository.save(order);
    }

    @Override
    public List<DessertOrder> getAllDessertOrders() {
        return dessertOrderRepository.findAll();
    }

    @Override
    @Transactional
    public DessertOrder updateDessertOrder(Long orderId, DessertOrderRequest request) {
        DessertOrder existingOrder = dessertOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Dessert order not found"));

        Dessert dessert = dessertRepository.findById(request.getDessertId())
                .orElseThrow(() -> new RuntimeException("Dessert not found"));

        existingOrder.setDessert(dessert);
        existingOrder.setQuantity(request.getQuantity());
        existingOrder.setCustomerName(request.getCustomerName());
        existingOrder.setTotalPrice(dessert.getPrice().multiply(
                java.math.BigDecimal.valueOf(request.getQuantity())));

        return dessertOrderRepository.save(existingOrder);
    }
}
