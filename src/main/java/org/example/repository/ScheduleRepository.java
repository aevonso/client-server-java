package org.example.repository;

import org.example.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByDate(LocalDate date);

    @Query("SELECT s FROM Schedule s WHERE s.date BETWEEN :startDate AND :endDate")
    List<Schedule> findByDateBetween(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);
}
