package org.example.repository;

import org.example.model.Dessert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DessertRepository extends JpaRepository<Dessert, Long> {
    Optional<Dessert> findByName(String name);
    boolean existsByName(String name);
}
