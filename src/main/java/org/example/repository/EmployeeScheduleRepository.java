package org.example.repository;

import org.example.model.Employee;
import org.example.model.EmployeeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, Long> {
    List<EmployeeSchedule> findByEmployeeAndWorkDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    List<EmployeeSchedule> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT es FROM EmployeeSchedule es WHERE es.employee = :employee AND es.workDate >= :startDate AND es.workDate <= :endDate ORDER BY es.workDate, es.startTime")
    List<EmployeeSchedule> findEmployeeWeeklySchedule(@Param("employee") Employee employee,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT es FROM EmployeeSchedule es WHERE es.employee.position = :position AND es.workDate >= :startDate AND es.workDate <= :endDate ORDER BY es.employee.lastName, es.workDate, es.startTime")
    List<EmployeeSchedule> findPositionWeeklySchedule(@Param("position") String position,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);
}
