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
}
