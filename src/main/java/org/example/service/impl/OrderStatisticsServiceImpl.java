package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.CoffeeOrder;
import org.example.model.Customer;
import org.example.model.DessertOrder;
import org.example.repository.CoffeeOrderRepository;
import org.example.repository.CustomerRepository;
import org.example.repository.DessertOrderRepository;
import org.example.service.OrderStatisticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;

@Service
@RequiredArgsConstructor
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    private final CoffeeOrderRepository coffeeOrderRepository;
    private final DessertOrderRepository dessertOrderRepository;
    private final CustomerRepository customerRepository;


    @Override
    public Map<String, Object> getOrdersByDate(LocalDate date) {
        Map<String, Object> result = new HashMap<>();

        List<CoffeeOrder> coffeeOrders = coffeeOrderRepository.findByOrderDate(date);
        List<DessertOrder> dessertOrders = dessertOrderRepository.findByOrderDate(date);

        result.put("date", date);
        result.put("coffeeOrders", coffeeOrders);
        result.put("dessertOrders", dessertOrders);
        result.put("totalOrders", coffeeOrders.size() + dessertOrders.size());

        return result;
    }

    @Override
    public Map<String, Object> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> result = new HashMap<>();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<CoffeeOrder> coffeeOrders = coffeeOrderRepository.findByOrderDateBetween(startDateTime, endDateTime);
        List<DessertOrder> dessertOrders = dessertOrderRepository.findByOrderDateBetween(startDateTime, endDateTime);

        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("coffeeOrders", coffeeOrders);
        result.put("dessertOrders", dessertOrders);
        result.put("totalCoffeeOrders", coffeeOrders.size());
        result.put("totalDessertOrders", dessertOrders.size());
        result.put("totalOrders", coffeeOrders.size() + dessertOrders.size());

        return result;
    }

    @Override
    public Long getDessertOrdersCountByDate(LocalDate date) {
        return dessertOrderRepository.countByOrderDate(date);
    }

    @Override
    public Long getCoffeeOrdersCountByDate(LocalDate date) {
        return coffeeOrderRepository.countByOrderDate(date);
    }


    @Override
    public List<Map<String, Object>> getTodayCustomersWithBaristaInfo() {
        List<Map<String, Object>> result = new ArrayList<>();

        List<CoffeeOrder> todayOrders = coffeeOrderRepository.findOrdersWithBaristaByDate(LocalDate.now());

        for (CoffeeOrder order : todayOrders) {
            if (order.getCustomer() != null && order.getBarista() != null) {
                Map<String, Object> info = new HashMap<>();
                info.put("customer", order.getCustomer());
                info.put("barista", order.getBarista());
                info.put("order", order);
                result.add(info);
            }
        }

        return result;
    }

    @Override
    public Double getAverageOrderTotalByDate(LocalDate date) {
        Double coffeeAvg = coffeeOrderRepository.findAverageOrderTotalByDate(date);
        Double dessertAvg = dessertOrderRepository.findByOrderDate(date).stream()
                .mapToDouble(do -> do.getTotalPrice().doubleValue())
                .average()
                .orElse(0.0);


        long coffeeCount = coffeeOrderRepository.countByOrderDate(date);
        long dessertCount = dessertOrderRepository.countByOrderDate(date);
        long totalCount = coffeeCount + dessertCount;

        if (totalCount == 0) return 0.0;

        double totalSum = (coffeeAvg != null ? coffeeAvg * coffeeCount : 0) +
                (dessertAvg * dessertCount);

        return totalSum / totalCount;
    }

    @Override
    public Double getMaxOrderTotalByDate(LocalDate date) {
        Double coffeeMax = coffeeOrderRepository.findMaxOrderTotalByDate(date);
        Double dessertMax = dessertOrderRepository.findByOrderDate(date).stream()
                .mapToDouble(do -> do.getTotalPrice().doubleValue())
                .max()
                .orElse(0.0);

        return Math.max(coffeeMax != null ? coffeeMax : 0, dessertMax);
    }

    @Override
    public Map<String, Object> getCustomerWithMaxOrderTotalByDate(LocalDate date) {
        List<Object[]> coffeeResults = coffeeOrderRepository.findCustomerWithMaxOrderTotalByDate(date);

        if (coffeeResults.isEmpty()) {
            return Map.of("message", "No orders found for the specified date");
        }

        Object[] maxResult = coffeeResults.get(0);
        Customer customer = (Customer) maxResult[0];
        Double total = (Double) maxResult[1];

        Map<String, Object> result = new HashMap<>();
        result.put("customer", customer);
        result.put("totalSpent", total);
        result.put("date", date);

        return result;
    }

    @Override
    public Map<String, Object> getDailyStatistics(LocalDate date) {
        Map<String, Object> stats = new HashMap<>();

        stats.put("date", date);
        stats.put("coffeeOrdersCount", getCoffeeOrdersCountByDate(date));
        stats.put("dessertOrdersCount", getDessertOrdersCountByDate(date));
        stats.put("averageOrderTotal", getAverageOrderTotalByDate(date));
        stats.put("maxOrderTotal", getMaxOrderTotalByDate(date));
        stats.put("customerWithMaxOrder", getCustomerWithMaxOrderTotalByDate(date));

        return stats;
    }
}
