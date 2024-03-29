package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.Schedule;
import br.ufpr.aquitemsus.model.ScheduleReport;
import br.ufpr.aquitemsus.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedules")
public class ScheduleController {

    private final ScheduleService _scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        _scheduleService = scheduleService;
    }

    @GetMapping("/{id}")
    public Schedule findScheduleById(@PathVariable Long id) {
        return _scheduleService.findScheduleById(id);
    }

    @GetMapping(value = "/days-of-month", params = {"month", "year", "idEstablishment"})
    public List<Integer> findDaysOfMonthWithSchedules(
        @RequestParam Integer month,
        @RequestParam Integer year,
        @RequestParam Long idEstablishment
    ) {
        return _scheduleService.findDaysOfMonthWithSchedules(month, year, idEstablishment);
    }

    @GetMapping(value = "/days-of-month", params = {"month", "year", "idEstablishment", "idProfessional"})
    public List<Integer> findDaysOfMonthWithSchedules(
            @RequestParam Integer month,
            @RequestParam Integer year,
            @RequestParam Long idEstablishment,
            @RequestParam Long idProfessional
    ) {
        return _scheduleService.findDaysOfMonthWithSchedules(month, year, idEstablishment, idProfessional);
    }

    @GetMapping("/reserved")
    public List<Schedule> findReservedSchedules(@RequestParam Long idEstablishment) {
        return _scheduleService.findReservedSchedules(idEstablishment);
    }

    @GetMapping("/user")
    public List<Schedule> findUserSchedules(@RequestParam Long userId) {
        return _scheduleService.findUserSchedules(userId);
    }

    @GetMapping("/count-user")
    public Long countUserSchedules(@RequestParam Long userId) {
        return _scheduleService.countUserSchedules(userId);
    }

    @GetMapping(params = {"day", "month", "year", "idEstablishment"})
    public List<Schedule> findSchedulesOfDay(
            @RequestParam Integer day,
            @RequestParam Integer month,
            @RequestParam Integer year,
            @RequestParam Long idEstablishment
    ) {
        return _scheduleService.findSchedulesOfDay(day, month, year, idEstablishment);
    }

    @GetMapping(params = {"day", "month", "year", "idEstablishment", "idProfessional"})
    public List<Schedule> findSchedulesOfDay(
            @RequestParam Integer day,
            @RequestParam Integer month,
            @RequestParam Integer year,
            @RequestParam Long idEstablishment,
            @RequestParam Long idProfessional
    ) {
        return _scheduleService.findSchedulesOfDay(day, month, year, idEstablishment, idProfessional);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Schedule saveSchedule(@RequestBody Schedule schedule) {
        return _scheduleService.saveSchedule(schedule);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        return _scheduleService.updateSchedule(id, schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        _scheduleService.deleteSchedule(id);
    }

    @GetMapping("/report")
    public ScheduleReport getScheduleEstablishmentReport(@RequestParam Long idEstablishment) {
        return _scheduleService.getScheduleEstablishmentReport(idEstablishment);
    }
}
