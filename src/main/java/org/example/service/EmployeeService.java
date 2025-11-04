package org.example.service;

import org.example.model.Employee;
import org.example.model.EmployeeSchedule;
import org.example.model.dto.EmployeeRequest;
import org.example.model.dto.EmployeeScheduleRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EmployeeService {

    Employee createEmployee(EmployeeRequest request);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Long employeeId, EmployeeRequest request);
    void deleteEmployee(Long employeeId);
    Employee getEmployeeById(Long employeeId);

    EmployeeSchedule createSchedule(EmployeeScheduleRequest request);
    List<EmployeeSchedule> getAllSchedules();

    List<EmployeeSchedule> getEmployeeWeeklySchedule(Long employeeId, LocalDate startDate);
    List<Map<String, Object>> getBaristasWeeklySchedule(LocalDate startDate);
    List<Map<String, Object>> getAllEmployeesWeeklySchedule(LocalDate startDate);

    List<Employee> getActiveBaristas();
    List<Employee> getEmployeesByPosition(String position);
}
