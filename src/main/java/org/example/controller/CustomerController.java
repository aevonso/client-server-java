package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Customer;
import org.example.model.dto.CustomerRequest;
import org.example.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Базовые CRUD операции
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequest request) {
        Customer customer = customerService.createCustomer(request);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request) {
        Customer customer = customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    // ЗАДАНИЕ 1 - Скидки
    @GetMapping("/discounts/min")
    public ResponseEntity<Double> getMinDiscount() {
        Double minDiscount = customerService.getMinDiscount();
        return ResponseEntity.ok(minDiscount);
    }

    @GetMapping("/discounts/max")
    public ResponseEntity<Double> getMaxDiscount() {
        Double maxDiscount = customerService.getMaxDiscount();
        return ResponseEntity.ok(maxDiscount);
    }

    @GetMapping("/discounts/average")
    public ResponseEntity<Double> getAverageDiscount() {
        Double averageDiscount = customerService.getAverageDiscount();
        return ResponseEntity.ok(averageDiscount);
    }

    @GetMapping("/discounts/min-customers")
    public ResponseEntity<List<Customer>> getCustomersWithMinDiscount() {
        List<Customer> customers = customerService.getCustomersWithMinDiscount();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/discounts/max-customers")
    public ResponseEntity<List<Customer>> getCustomersWithMaxDiscount() {
        List<Customer> customers = customerService.getCustomersWithMaxDiscount();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/discounts/statistics")
    public ResponseEntity<Map<String, Object>> getDiscountStatistics() {
        Map<String, Object> statistics = customerService.getDiscountStatistics();
        return ResponseEntity.ok(statistics);
    }

    // ЗАДАНИЕ 2 - Клиенты
    @GetMapping("/youngest")
    public ResponseEntity<List<Customer>> getYoungestCustomers() {
        List<Customer> customers = customerService.getYoungestCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/oldest")
    public ResponseEntity<List<Customer>> getOldestCustomers() {
        List<Customer> customers = customerService.getOldestCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/birthday-today")
    public ResponseEntity<List<Customer>> getCustomersWithBirthdayToday() {
        List<Customer> customers = customerService.getCustomersWithBirthdayToday();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/without-email")
    public ResponseEntity<List<Customer>> getCustomersWithoutEmail() {
        List<Customer> customers = customerService.getCustomersWithoutEmail();
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/birthday")
    public ResponseEntity<List<Customer>> getCustomersWithBirthday(
            @RequestParam int month,
            @RequestParam int day) {
        List<Customer> customers = customerService.getCustomersWithBirthday(month, day);
        return ResponseEntity.ok(customers);
    }
}
