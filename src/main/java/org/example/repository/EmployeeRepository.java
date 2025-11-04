package org.example.repository;
import org.example.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByPosition(String position);
    List<Employee> findByIsActiveTrue();

    @Query("SELECT e FROM Employee e WHERE e.position = 'BARISTA' AND e.isActive = true")
    List<Employee> findActiveBaristas();
}