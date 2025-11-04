package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.Customer;
import org.example.model.dto.CustomerRequest;
import org.example.repository.CustomerRepository;
import org.example.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


    @Service
    @RequiredArgsConstructor
    public class CustomerServiceImpl implements CustomerService {

        private final CustomerRepository customerRepository;

        @Override
        @Transactional
        public Customer createCustomer(CustomerRequest request) {
            Customer customer = new Customer();
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setBirthDate(request.getBirthDate());
            customer.setDiscount(request.getDiscount());
            customer.setRegistrationDate(LocalDateTime.now());

            return customerRepository.save(customer);
        }

        @Override
        public List<Customer> getAllCustomers() {
            return customerRepository.findAll();
        }

        @Override
        @Transactional
        public Customer updateCustomer(Long customerId, CustomerRequest request) {
            Customer existingCustomer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            existingCustomer.setFirstName(request.getFirstName());
            existingCustomer.setLastName(request.getLastName());
            existingCustomer.setEmail(request.getEmail());
            existingCustomer.setPhone(request.getPhone());
            existingCustomer.setBirthDate(request.getBirthDate());
            existingCustomer.setDiscount(request.getDiscount());

            return customerRepository.save(existingCustomer);
        }

        @Override
        @Transactional
        public void deleteCustomer(Long customerId) {
            if (!customerRepository.existsById(customerId)) {
                throw new RuntimeException("Customer not found");
            }
            customerRepository.deleteById(customerId);
        }

        @Override
        public Customer getCustomerById(Long customerId) {
            return customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        }


        @Override
        public Double getMinDiscount() {
            return customerRepository.findMinDiscount();
        }

        @Override
        public Double getMaxDiscount() {
            return customerRepository.findMaxDiscount();
        }

        @Override
        public Double getAverageDiscount() {
            return customerRepository.findAverageDiscount();
        }

        @Override
        public List<Customer> getCustomersWithMinDiscount() {
            return customerRepository.findCustomersWithMinDiscount();
        }

        @Override
        public List<Customer> getCustomersWithMaxDiscount() {
            return customerRepository.findCustomersWithMaxDiscount();
        }


        @Override
        public List<Customer> getYoungestCustomers() {
            return customerRepository.findYoungestCustomer();
        }

        @Override
        public List<Customer> getOldestCustomers() {
            return customerRepository.findOldestCustomer();
        }

        @Override
        public List<Customer> getCustomersWithBirthdayToday() {
            return customerRepository.findCustomersWithBirthdayToday();
        }

        @Override
        public List<Customer> getCustomersWithoutEmail() {
            return customerRepository.findByEmailIsNull();
        }


        @Override
        public List<Customer> getCustomersWithBirthday(int month, int day) {
            return customerRepository.findCustomersByBirthday(month, day);
        }

        @Override
        public Map<String, Object> getDiscountStatistics() {
            Map<String, Object> stats = new HashMap<>();
            stats.put("minDiscount", getMinDiscount());
            stats.put("maxDiscount", getMaxDiscount());
            stats.put("averageDiscount", getAverageDiscount());
            stats.put("customersWithMinDiscount", getCustomersWithMinDiscount());
            stats.put("customersWithMaxDiscount", getCustomersWithMaxDiscount());
            return stats;
        }
    }

