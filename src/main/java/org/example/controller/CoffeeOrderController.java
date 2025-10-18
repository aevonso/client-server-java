package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.CoffeeOrder;
import org.example.model.dto.CoffeeOrderRequest;
import org.example.service.CoffeeOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/coffee-orders")
@RequiredArgsConstructor
public class CoffeeOrderController {

    private final CoffeeOrderService coffeeOrderService;

    @PostMapping
    public ResponseEntity<CoffeeOrder> createCoffeeOrder(@Valid @RequestBody CoffeeOrderRequest request) {
        CoffeeOrder order = coffeeOrderService.createCoffeeOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<CoffeeOrder>> getAllCoffeeOrders() {
        List<CoffeeOrder> orders = coffeeOrderService.getAllCoffeeOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<CoffeeOrder> updateCoffeeOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody CoffeeOrderRequest request) {
        CoffeeOrder order = coffeeOrderService.updateCoffeeOrder(orderId, request);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/orderId")
    public ResponseEntity<Void> deleteCoffeeOrder(@PathVariable Long orderId) {
        coffeeOrderService.deleteCoffeeOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/waiter/{waiterName}")
    public ResponseEntity<List<CoffeeOrder>> getOrdersByWaiter(@PathVariable String waiterName) {
        List<CoffeeOrder> orders = coffeeOrderService.getOrdersByWaiter(waiterName);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<CoffeeOrder>> getOrdersByCustomer(@PathVariable String customerName) {
        List<CoffeeOrder> orders = coffeeOrderService.getOrdersByCustomer(customerName);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/phone/{phone}")
    public ResponseEntity<List<CoffeeOrder>> getOrdersByCustomerPhone(@PathVariable String phone) {
        List<CoffeeOrder> orders = coffeeOrderService.getOrdersByCustomerPhone(phone);
        return ResponseEntity.ok(orders);
    }
}
