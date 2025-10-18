package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.DessertOrder;
import org.example.model.dto.DessertOrderRequest;
import org.example.service.DessertOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/dessert-orders")
@RequiredArgsConstructor
public class DessertOrderController {

    private final DessertOrderService dessertOrderService;

    @PostMapping
    public ResponseEntity<DessertOrder> createDessertOrder(@Valid @RequestBody DessertOrderRequest request) {
        DessertOrder order = dessertOrderService.createDessertOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<DessertOrder>> getAllDessertOrders() {
        List<DessertOrder> orders = dessertOrderService.getAllDessertOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<DessertOrder> updateDessertOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody DessertOrderRequest request) {
        DessertOrder order = dessertOrderService.updateDessertOrder(orderId, request);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteDessertOrder(@PathVariable Long orderId) {
        dessertOrderService.deleteDessertOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/dessert/{dessertId}")
    public ResponseEntity<Void> deleteOrdersByDessert(@PathVariable Long dessertId) {
        dessertOrderService.deleteOrdersByDessert(dessertId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dessert/{dessertId}")
    public ResponseEntity<List<DessertOrder>> getOrdersByDessert(@PathVariable Long dessertId) {
        List<DessertOrder> orders = dessertOrderService.getOrdersByDessert(dessertId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/waiter/{waiterName}")
    public ResponseEntity<List<DessertOrder>> getOrdersByWaiter(@PathVariable String waiterName) {
        List<DessertOrder> orders = dessertOrderService.getOrdersByWaiter(waiterName);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<DessertOrder>> getOrdersByCustomer(@PathVariable String customerName) {
        List<DessertOrder> orders = dessertOrderService.getOrdersByCustomer(customerName);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/phone/{phone}")
    public ResponseEntity<List<DessertOrder>> getOrdersByCustomerPhone(@PathVariable String phone) {
        List<DessertOrder> orders = dessertOrderService.getOrdersByCustomerPhone(phone);
        return ResponseEntity.ok(orders);
    }
}
