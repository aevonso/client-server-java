package org.example.repository;

import org.example.model.CoffeeOrder;
import org.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface CoffeeOrderRepository extends  JpaRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByWaiterNameContainingIgnoreCase(String waiterName);
    List<CoffeeOrder> findByCustomerNameContainingIgnoreCase(String customerName);
    List<CoffeeOrder> findByCustomerPhone(String customerPhone);

    @Query("SELECT co FROM CoffeeOrder co WHERE co.waiterName = :waiterName")
    List<CoffeeOrder> findOrdersByWaiter(@Param("waiterName") String waiterName);

    List<CoffeeOrder> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT co FROM CoffeeOrder co WHERE DATE(co.orderDate) = :date")
    List<CoffeeOrder> findByOrderDate(@Param("date") LocalDate date);

    @Query("SELECT COUNT(co) FROM CoffeeOrder co WHERE DATE(co.orderDate) = :date")
    Long countByOrderDate(@Param("date") LocalDate date);


    @Query("SELECT DISTINCT co.customer FROM CoffeeOrder co WHERE DATE(co.orderDate) = CURRENT_DATE AND co.customer IS NOT NULL")
    List<Customer> findCustomersWithOrdersToday();

    @Query("SELECT co FROM CoffeeOrder co WHERE DATE(co.orderDate) = :date AND co.barista IS NOT NULL")
    List<CoffeeOrder> findOrdersWithBaristaByDate(@Param("date") LocalDate date);

    @Query("SELECT AVG(co.totalPrice) FROM CoffeeOrder co WHERE DATE(co.orderDate) = :date")
    Double findAverageOrderTotalByDate(@Param("date") LocalDate date);

    @Query("SELECT MAX(co.totalPrice) FROM CoffeeOrder co WHERE DATE(co.orderDate) = :date")
    Double findMaxOrderTotalByDate(@Param("date") LocalDate date);

    @Query("SELECT co.customer, SUM(co.totalPrice) as total FROM CoffeeOrder co WHERE DATE(co.orderDate) = :date GROUP BY co.customer ORDER BY total DESC")
    List<Object[]> findCustomerWithMaxOrderTotalByDate(@Param("date") LocalDate date);
}

