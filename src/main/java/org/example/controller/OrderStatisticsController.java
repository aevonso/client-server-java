package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.OrderStatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class OrderStatisticsController {

    private final OrderStatisticsService orderStatisticsService;

    // ЗАДАНИЕ 3
    @GetMapping("/orders/date/{date}")
    public ResponseEntity<Map<String, Object>> getOrdersByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Map<String, Object> orders = orderStatisticsService.getOrdersByDate(date);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/range")
    public ResponseEntity<Map<String, Object>> getOrdersBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> orders = orderStatisticsService.getOrdersBetweenDates(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/dessert-orders/count/{date}")
    public ResponseEntity<Long> getDessertOrdersCountByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Long count = orderStatisticsService.getDessertOrdersCountByDate(date);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/coffee-orders/count/{date}")
    public ResponseEntity<Long> getCoffeeOrdersCountByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Long count = orderStatisticsService.getCoffeeOrdersCountByDate(date);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/today-customers-with-barista")
    public ResponseEntity<List<Map<String, Object>>> getTodayCustomersWithBaristaInfo() {
        List<Map<String, Object>> customers = orderStatisticsService.getTodayCustomersWithBaristaInfo();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/average-order-total/{date}")
    public ResponseEntity<Double> getAverageOrderTotalByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Double average = orderStatisticsService.getAverageOrderTotalByDate(date);
        return ResponseEntity.ok(average);
    }

    @GetMapping("/max-order-total/{date}")
    public ResponseEntity<Double> getMaxOrderTotalByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Double max = orderStatisticsService.getMaxOrderTotalByDate(date);
        return ResponseEntity.ok(max);
    }

    @GetMapping("/customer-max-order/{date}")
    public ResponseEntity<Map<String, Object>> getCustomerWithMaxOrderTotalByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Map<String, Object> customer = orderStatisticsService.getCustomerWithMaxOrderTotalByDate(date);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/daily/{date}")
    public ResponseEntity<Map<String, Object>> getDailyStatistics(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Map<String, Object> stats = orderStatisticsService.getDailyStatistics(date);
        return ResponseEntity.ok(stats);
    }
}
