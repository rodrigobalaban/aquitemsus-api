package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.exception.NotFoundException;
import br.ufpr.aquitemsus.model.Schedule;
import br.ufpr.aquitemsus.model.ScheduleReport;
import br.ufpr.aquitemsus.model.enums.ScheduleStatus;
import br.ufpr.aquitemsus.model.interfaces.SchedulePerMonth;
import br.ufpr.aquitemsus.repository.ScheduleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository _scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        _scheduleRepository = scheduleRepository;
    }

    public Schedule findScheduleById(Long id) {
        return this._scheduleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Integer> findDaysOfMonthWithSchedules(Integer month, Integer year, Long idEstablishment) {
        return this._scheduleRepository.findDaysOfMonthWithSchedules(month, year, idEstablishment);
    }

    public List<Integer> findDaysOfMonthWithSchedules(Integer month, Integer year, Long idEstablishment, Long idProfessional) {
        var status = ScheduleStatus.Available;
        return this._scheduleRepository.findDaysOfMonthWithSchedules(month, year, idEstablishment, idProfessional, status);
    }

    public List<Schedule> findReservedSchedules(Long establishmentId) {
        var status = ScheduleStatus.Reserved;
        return this._scheduleRepository.findAllByEstablishmentIdAndStatus(establishmentId, status);
    }

    public List<Schedule> findUserSchedules(Long userId) {
        return this._scheduleRepository.findAllByUserSusIdOrderByDateDesc(userId);
    }

    public Long countUserSchedules(Long userId) {
        var confirmed = ScheduleStatus.Confirmed;
        var reserved = ScheduleStatus.Reserved;

        return this._scheduleRepository.countAllByUserSusIdAndStatusOrStatus(userId, confirmed, reserved);
    }

    public List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment) {
        return this._scheduleRepository.findSchedulesOfDay(day, month, year, idEstablishment);
    }

    public List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment, Long idProfessional) {
        return this._scheduleRepository.findSchedulesOfDay(day, month, year, idEstablishment, idProfessional);
    }

    public List<Schedule> findSchedulesBetween(Date start, Date end) {
        return this._scheduleRepository.findAllByDateBetween(start, end);
    }

    public Schedule saveSchedule(Schedule schedule) {
        return this._scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        var schedule = this.findScheduleById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Employee"))) {
            schedule.setDate(updatedSchedule.getDate());
            schedule.setEstablishment(updatedSchedule.getEstablishment());
            schedule.setProfessional(updatedSchedule.getProfessional());
            schedule.setStatus(updatedSchedule.getStatus());
        } else {
            schedule.setStatus(ScheduleStatus.Reserved);
        }

        schedule.setUserSus(updatedSchedule.getUserSus());

        return this.saveSchedule(schedule);
    }

    public void deleteSchedule(Long id) {
        this._scheduleRepository.deleteById(id);
    }

    public ScheduleReport getScheduleEstablishmentReport(Long idEstablishment) {
        ScheduleReport report = new ScheduleReport();

        ScheduleStatus statusCanceled = ScheduleStatus.Canceled;
        Long schedulesToday = _scheduleRepository.countSchedulesTodayWithoutCanceled(idEstablishment, statusCanceled);
        report.setSchedulesToday(schedulesToday);

        ScheduleStatus statusReserved = ScheduleStatus.Reserved;
        Long schedulesReserved = _scheduleRepository.countAllByEstablishmentIdAndStatus(idEstablishment, statusReserved);
        report.setSchedulesReserved(schedulesReserved);

        ScheduleStatus statusComplete = ScheduleStatus.Complete;
        Long schedulesComplete = _scheduleRepository.countAllByEstablishmentIdAndStatus(idEstablishment, statusComplete);
        ScheduleStatus statusRated = ScheduleStatus.Rated;
        Long schedulesRated = _scheduleRepository.countAllByEstablishmentIdAndStatus(idEstablishment, statusRated);
        ScheduleStatus statusAbsent = ScheduleStatus.Absent;
        Long schedulesAbsent = _scheduleRepository.countAllByEstablishmentIdAndStatus(idEstablishment, statusAbsent);

        long totalSchedules = schedulesComplete + schedulesRated + schedulesAbsent;
        Long attendancePercent = ((totalSchedules - schedulesAbsent) * 100) / totalSchedules;
        report.setSchedulesAttendance(attendancePercent);

        List<SchedulePerMonth> schedulePerMonthList = _scheduleRepository.countAllByEstablishmentPerMonth(idEstablishment);
        report.setSchedulesPerMonth(schedulePerMonthList);

        return report;
    }
}
