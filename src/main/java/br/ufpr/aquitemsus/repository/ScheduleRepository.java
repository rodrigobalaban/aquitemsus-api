package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT DISTINCT day(s.date) FROM Schedule s WHERE month(s.date) = :month AND year(s.date) = :year AND s.establishment.id = :idEstablishment")
    List<Integer> findDaysOfMonthWithSchedules(Integer month, Integer year, Long idEstablishment);

    @Query("SELECT s FROM Schedule s WHERE day(s.date) = :day AND month(s.date) = :month AND year(s.date) = :year AND s.establishment.id = :idEstablishment")
    List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment);
}
