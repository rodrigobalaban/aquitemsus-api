package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Schedule;
import br.ufpr.aquitemsus.model.enums.ScheduleStatus;
import br.ufpr.aquitemsus.model.interfaces.SchedulePerMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT DISTINCT day(s.date) " +
           "  FROM Schedule s" +
           " WHERE month(s.date) = :month " +
           "   AND year(s.date) = :year " +
           "   AND s.establishment.id = :idEstablishment ")
    List<Integer> findDaysOfMonthWithSchedules(Integer month, Integer year, Long idEstablishment);

    @Query("SELECT DISTINCT day(s.date) " +
           "  FROM Schedule s " +
           " WHERE s.date > current_timestamp " +
           "   AND month(s.date) = :month" +
           "   AND year(s.date) = :year " +
           "   AND s.establishment.id = :idEstablishment " +
           "   AND s.professional.id = :idProfessional " +
           "   AND s.status = :status")
    List<Integer> findDaysOfMonthWithSchedules(
            Integer month,
            Integer year,
            Long idEstablishment,
            Long idProfessional,
            ScheduleStatus status);

    @Query("SELECT s " +
           "  FROM Schedule s " +
           " WHERE day(s.date) = :day " +
           "   AND month(s.date) = :month " +
           "   AND year(s.date) = :year " +
           "   AND s.establishment.id = :idEstablishment")
    List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment);

    @Query("SELECT s " +
           "  FROM Schedule s " +
           " WHERE s.date > current_timestamp " +
           "   AND day(s.date) = :day " +
           "   AND month(s.date) = :month " +
           "   AND year(s.date) = :year " +
           "   AND s.establishment.id = :idEstablishment " +
           "   AND s.professional.id = :idProfessional")
    List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment, Long idProfessional);

    @Query("SELECT s FROM Schedule s WHERE s.date >= :start AND s.date < :end")
    List<Schedule> findAllByDateBetween(Date start, Date end);

    @Query("SELECT COUNT(s) " +
           "  FROM Schedule s" +
           " WHERE s.date = current_date " +
           "   AND s.establishment.id = :idEstablishment AND s.status <> :statusCanceled")
    Long countSchedulesTodayWithoutCanceled(Long idEstablishment, ScheduleStatus statusCanceled);

    List<Schedule> findAllByEstablishmentIdAndStatus(Long establishmentId, ScheduleStatus status);

    List<Schedule> findAllByUserSusIdOrderByDateDesc(Long userSusId);

    Long countAllByEstablishmentIdAndStatus(Long establishmentId, ScheduleStatus status);

    Long countAllByUserSusIdAndStatusOrStatus(Long userSusId, ScheduleStatus statusConfirmed, ScheduleStatus statusReserved);

    @Query("SELECT COUNT(s) AS schedules, MONTH(s.date) as month, YEAR(s.date) as year " +
           "  FROM Schedule s " +
           " WHERE s.establishment.id = :establishmentId " +
           " GROUP BY YEAR(s.date), MONTH(s.date) ORDER BY 3, 2")
    List<SchedulePerMonth> countAllByEstablishmentPerMonth(Long establishmentId);
}
