package org.example.repository;

import org.example.model.Dessert;
import org.example.model.DessertOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DessertOrderRepository extends JpaRepository<DessertOrder, Long> {
    List<DessertOrder> findByDessert(Dessert dessert);
    List<DessertOrder> findByWaiterNameContainingIgnoreCase(String waiterName);
    List<DessertOrder> findByCustomerNameContainingIgnoreCase(String customerName);
    List<DessertOrder> findByCustomerPhone(String customerPhone);

    @Modifying
    @Query("DELETE FROM DessertOrder do WHERE do.dessert = :dessert")
    void deleteByDessert(@Param("dessert") Dessert dessert);
}
