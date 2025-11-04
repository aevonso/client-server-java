package org.example.repository;

import org.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    List<Customer> findByEmailIsNull();

    //clients min discount
    @Query("SELECT c FROM Customer c WHERE c.discount = (SELECT MIN(c2.discount) FROM Customer c2)")
    List<Customer> findCustomersWithMinDiscount();

    //clients max discount
    @Query("SELECT c FROM Customer c WHERE c.discount = (SELECT MAX(c2.discount) FROM Customer c2)")
    List<Customer> findCustomersWithMaxDiscount();

    //very young client
    @Query("SELECT c FROM Customer c WHERE c.birthDate = (SELECT MAX(c2.birthDate) FROM Customer c2)")
    List<Customer> findYoungestCustomer();

    //very older client
    @Query("SELECT c FROM Customer c WHERE c.birthDate = (SELECT MIN(c2.birthDate) FROM Customer c2)")
    List<Customer> findOldestCustomer();

    @Query("SELECT c FROM Customer c WHERE FUNCTION('MONTH', c.birthDate) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('DAY', c.birthDate) = FUNCTION('DAY', CURRENT_DATE)")
    List<Customer> findCustomersWithBirthdayToday();

    @Query("SELECT c FROM Customer c WHERE FUNCTION('MONTH', c.birthDate) = :month AND FUNCTION('DAY', c.birthDate) = :day")
    List<Customer> findCustomersByBirthday(@Param("month") int month, @Param("day") int day);

    @Query("SELECT MIN(c.discount) FROM Customer c")
    Double findMinDiscount();

    @Query("SELECT MAX(c.discount) FROM Customer c")
    Double findMaxDiscount();

    @Query("SELECT AVG(c.discount) FROM Customer c")
    Double findAverageDiscount();
}
