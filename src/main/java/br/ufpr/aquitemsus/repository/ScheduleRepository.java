package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Schedule;
import br.ufpr.aquitemsus.model.enums.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT DISTINCT day(s.date) FROM Schedule s WHERE month(s.date) = :month AND year(s.date) = :year AND s.establishment.id = :idEstablishment ")
    List<Integer> findDaysOfMonthWithSchedules(Integer month, Integer year, Long idEstablishment);

    @Query("SELECT DISTINCT day(s.date) FROM Schedule s WHERE s.date > current_timestamp AND month(s.date) = :month AND year(s.date) = :year AND s.establishment.id = :idEstablishment AND s.professional.id = :idProfessional AND s.status = :status")
    List<Integer> findDaysOfMonthWithSchedules(Integer month, Integer year, Long idEstablishment, Long idProfessional, ScheduleStatus status);

    List<Schedule> findAllByEstablishmentIdAndStatus(Long establishmentId, ScheduleStatus status);

    List<Schedule> findAllByUserSusIdOrderByDateDesc(Long userSusId);

    Long countAllByUserSusIdAndStatusOrStatus(Long userSusId, ScheduleStatus statusConfirmed, ScheduleStatus statusReserved);

    @Query("SELECT s FROM Schedule s WHERE day(s.date) = :day AND month(s.date) = :month AND year(s.date) = :year AND s.establishment.id = :idEstablishment")
    List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment);

    @Query("SELECT s FROM Schedule s WHERE s.date > current_timestamp AND day(s.date) = :day AND month(s.date) = :month AND year(s.date) = :year AND s.establishment.id = :idEstablishment AND s.professional.id = :idProfessional")
    List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment, Long idProfessional);

    @Query("SELECT s FROM Schedule s WHERE s.date >= :start AND s.date < :end")
    List<Schedule> findAllByDateBetween(Date start, Date end);
}
