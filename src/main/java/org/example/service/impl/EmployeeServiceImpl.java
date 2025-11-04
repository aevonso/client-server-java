package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.Employee;
import org.example.model.EmployeeSchedule;
import org.example.model.dto.EmployeeRequest;
import org.example.model.dto.EmployeeScheduleRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.EmployeeScheduleRepository;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeScheduleRepository employeeScheduleRepository;

    @Override
    @Transactional
    public Employee createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPosition(request.getPosition());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setHireDate(request.getHireDate());
        employee.setIsActive(request.getIsActive());

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long employeeId, EmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        existingEmployee.setPosition(request.getPosition());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setPhone(request.getPhone());
        existingEmployee.setHireDate(request.getHireDate());
        existingEmployee.setIsActive(request.getIsActive());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    @Transactional
    public EmployeeSchedule createSchedule(EmployeeScheduleRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeSchedule schedule = new EmployeeSchedule();
        schedule.setEmployee(employee);
        schedule.setWorkDate(request.getWorkDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setBreakStart(request.getBreakStart());
        schedule.setBreakEnd(request.getBreakEnd());
        schedule.setNotes(request.getNotes());

        return employeeScheduleRepository.save(schedule);
    }

    @Override
    public List<EmployeeSchedule> getAllSchedules() {
        return employeeScheduleRepository.findAll();
    }

    // ЗАДАНИЕ 5
    @Override
    public List<EmployeeSchedule> getEmployeeWeeklySchedule(Long employeeId, LocalDate startDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate endDate = startDate.plusDays(6); // неделя

        return employeeScheduleRepository.findEmployeeWeeklySchedule(employee, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getBaristasWeeklySchedule(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        List<EmployeeSchedule> schedules = employeeScheduleRepository.findPositionWeeklySchedule("BARISTA", startDate, endDate);

        return groupSchedulesByEmployee(schedules);
    }

    @Override
    public List<Map<String, Object>> getAllEmployeesWeeklySchedule(LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(6);
        List<EmployeeSchedule> schedules = employeeScheduleRepository.findByWorkDateBetween(startDate, endDate);

        return groupSchedulesByEmployee(schedules);
    }

    private List<Map<String, Object>> groupSchedulesByEmployee(List<EmployeeSchedule> schedules) {
        Map<Employee, List<EmployeeSchedule>> grouped = new HashMap<>();

        for (EmployeeSchedule schedule : schedules) {
            grouped.computeIfAbsent(schedule.getEmployee(), k -> new ArrayList<>()).add(schedule);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Employee, List<EmployeeSchedule>> entry : grouped.entrySet()) {
            Map<String, Object> employeeSchedule = new HashMap<>();
            employeeSchedule.put("employee", entry.getKey());
            employeeSchedule.put("schedules", entry.getValue());
            result.add(employeeSchedule);
        }

        return result;
    }

    @Override
    public List<Employee> getActiveBaristas() {
        return employeeRepository.findActiveBaristas();
    }

    @Override
    public List<Employee> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }
}
