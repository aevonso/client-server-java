package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.CoffeeOrder;
import org.example.model.CoffeeType;
import org.example.model.dto.CoffeeOrderRequest;
import org.example.repository.CoffeeOrderRepository;
import org.example.repository.CoffeeTypeRepository;
import org.example.service.CoffeeOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    private final CoffeeOrderRepository coffeeOrderRepository;
    private final CoffeeTypeRepository coffeeTypeRepository;

    @Override
    @Transactional
    public CoffeeOrder createCoffeeOrder(CoffeeOrderRequest request) {
        CoffeeType coffeeType = coffeeTypeRepository.findById(request.getCoffeeTypeId())
                .orElseThrow(() -> new RuntimeException("Coffee type not found"));

        if (Boolean.FALSE.equals(coffeeType.getIsAvailable())) {
            throw new RuntimeException("Coffee type is not available");
        }

        CoffeeOrder order = new CoffeeOrder();
        order.setCoffeeType(coffeeType);
        order.setQuantity(request.getQuantity());
        order.setCustomerName(request.getCustomerName());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(coffeeType.getPrice().multiply(
                java.math.BigDecimal.valueOf(request.getQuantity())));
        order.setStatus("PENDING");

        return coffeeOrderRepository.save(order);
    }

    @Override
    public List<CoffeeOrder> getAllCoffeeOrders() {
        return coffeeOrderRepository.findAll();
    }

    @Override
    @Transactional
    public CoffeeOrder updateCoffeeOrder(Long orderId, CoffeeOrderRequest request) {
        CoffeeOrder existingOrder = coffeeOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Coffee order not found"));

        CoffeeType coffeeType = coffeeTypeRepository.findById(request.getCoffeeTypeId())
                .orElseThrow(() -> new RuntimeException("Coffee type not found"));

        existingOrder.setCoffeeType(coffeeType);
        existingOrder.setQuantity(request.getQuantity());
        existingOrder.setCustomerName(request.getCustomerName());
        existingOrder.setTotalPrice(coffeeType.getPrice().multiply(
                java.math.BigDecimal.valueOf(request.getQuantity())));

        return coffeeOrderRepository.save(existingOrder);
    }

    @Override
    public void deleteCoffeeOrder(Long orderId) {
        if(!coffeeOrderRepository.existsById(orderId)) {
            throw new RuntimeException("Coffee order not found");
        }
        coffeeOrderRepository.deleteById(orderId);
    }

    @Override
    public List<CoffeeOrder> getOrdersByWaiter(String waiterName) {
        return coffeeOrderRepository.findByWaiterNameContainingIgnoreCase(waiterName);
    }

    @Override
    public List<CoffeeOrder> getOrdersByCustomer(String customerName) {
        return coffeeOrderRepository.findByCustomerNameContainingIgnoreCase(customerName);
    }

    @Override
    public List<CoffeeOrder> getOrdersByCustomerPhone(String customerPhone) {
        return coffeeOrderRepository.findByCustomerPhone(customerPhone);
    }
}