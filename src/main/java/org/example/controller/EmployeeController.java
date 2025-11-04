package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Employee;
import org.example.model.EmployeeSchedule;
import org.example.model.dto.EmployeeRequest;
import org.example.model.dto.EmployeeScheduleRequest;
import org.example.service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // Базовые CRUD операции
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee employee = employeeService.createEmployee(request);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeRequest request) {
        Employee employee = employeeService.updateEmployee(employeeId, request);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    // Расписание
    @PostMapping("/schedule")
    public ResponseEntity<EmployeeSchedule> createSchedule(@Valid @RequestBody EmployeeScheduleRequest request) {
        EmployeeSchedule schedule = employeeService.createSchedule(request);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<EmployeeSchedule>> getAllSchedules() {
        List<EmployeeSchedule> schedules = employeeService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    // ЗАДАНИЕ 5
    public ResponseEntity<List<EmployeeSchedule>> getEmployeeWeeklySchedule(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<EmployeeSchedule> schedule = employeeService.getEmployeeWeeklySchedule(employeeId, startDate);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/baristas/weekly-schedule")
    public ResponseEntity<List<Map<String, Object>>> getBaristasWeeklySchedule(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<Map<String, Object>> schedules = employeeService.getBaristasWeeklySchedule(startDate);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/all/weekly-schedule")
    public ResponseEntity<List<Map<String, Object>>> getAllEmployeesWeeklySchedule(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        List<Map<String, Object>> schedules = employeeService.getAllEmployeesWeeklySchedule(startDate);
        return ResponseEntity.ok(schedules);
    }


    @GetMapping("/position/{position}")
    public ResponseEntity<List<Employee>> getEmployeesByPosition(@PathVariable String position) {
        List<Employee> employees = employeeService.getEmployeesByPosition(position);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/baristas/active")
    public ResponseEntity<List<Employee>> getActiveBaristas() {
        List<Employee> baristas = employeeService.getActiveBaristas();
        return ResponseEntity.ok(baristas);
    }
}
