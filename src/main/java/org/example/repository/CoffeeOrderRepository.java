package org.example.repository;

import org.example.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CoffeeOrderRepository extends  JpaRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByWaiterNameContainingIgnoreCase(String waiterName);
    List<CoffeeOrder> findByCustomerNameContainingIgnoreCase(String customerName);
    List<CoffeeOrder> findByCustomerPhone(String customerPhone);

    @Query("SELECT co FROM CoffeeOrder co WHERE co.waiterName = :waiterName")
    List<CoffeeOrder> findOrdersByWaiter(@Param("waiterName") String waiterName);
}
