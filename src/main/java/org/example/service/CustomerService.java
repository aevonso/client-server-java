package org.example.service;


import org.example.model.Customer;
import org.example.model.dto.CustomerRequest;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    Customer createCustomer(CustomerRequest request);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Long customerId, CustomerRequest request);

    void deleteCustomer(Long customerId);

    Customer getCustomerById(Long customerId);

    Double getMinDiscount();

    Double getMaxDiscount();

    Double getAverageDiscount();

    List<Customer> getCustomersWithMinDiscount();

    List<Customer> getCustomersWithMaxDiscount();

    List<Customer> getYoungestCustomers();

    List<Customer> getOldestCustomers();

    List<Customer> getCustomersWithBirthdayToday();

    List<Customer> getCustomersWithoutEmail();

    List<Customer> getCustomersWithBirthday(int month, int day);

    Map<String, Object> getDiscountStatistics();

}
