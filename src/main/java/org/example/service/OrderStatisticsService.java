package org.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderStatisticsService {

    Map<String, Object> getOrdersByDate(LocalDate date);
    Map<String, Object> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate);
    Long getDessertOrdersCountByDate(LocalDate date);
    Long getCoffeeOrdersCountByDate(LocalDate date);

    List<Map<String, Object>> getTodayCustomersWithBaristaInfo();
    Double getAverageOrderTotalByDate(LocalDate date);
    Double getMaxOrderTotalByDate(LocalDate date);
    Map<String, Object> getCustomerWithMaxOrderTotalByDate(LocalDate date);

    Map<String, Object> getDailyStatistics(LocalDate date);
}
