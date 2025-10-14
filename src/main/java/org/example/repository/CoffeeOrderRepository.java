package org.example.repository;

import org.example.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeOrderRepository extends  JpaRepository<CoffeeOrder, Long> {

}
