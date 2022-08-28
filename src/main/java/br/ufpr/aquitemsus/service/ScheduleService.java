package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.exception.NotFoundException;
import br.ufpr.aquitemsus.model.Schedule;
import br.ufpr.aquitemsus.repository.ScheduleRepository;
import br.ufpr.aquitemsus.repository.UserAdminRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository _scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserAdminRepository userAdminRepository) {
        _scheduleRepository = scheduleRepository;
    }

    public Schedule findScheduleById(Long id) {
        return this._scheduleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Integer> findDaysOfMonthWithSchedules(Integer month, Integer year, Long idEstablishment) {
        return this._scheduleRepository.findDaysOfMonthWithSchedules(month, year, idEstablishment);
    }

    public List<Schedule> findSchedulesOfDay(Integer day, Integer month, Integer year, Long idEstablishment) {
        return this._scheduleRepository.findSchedulesOfDay(day, month, year, idEstablishment);
    }

    public Schedule saveSchedule(Schedule schedule) {
        return this._scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        var schedule = this.findScheduleById(id);

        schedule.setDate(updatedSchedule.getDate());
        schedule.setEstablishment(updatedSchedule.getEstablishment());
        schedule.setProfessional(updatedSchedule.getProfessional());
        schedule.setUserSus(updatedSchedule.getUserSus());
        schedule.setStatus(updatedSchedule.getStatus());

        return this.saveSchedule(schedule);
    }

    public void deleteSchedule(Long id) {
        this._scheduleRepository.deleteById(id);
    }
}
