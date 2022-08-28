package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.Schedule;
import br.ufpr.aquitemsus.service.ScheduleService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/days-of-month")
    public List<Integer> findDaysOfMonthWithSchedules(
        @RequestParam Integer month,
        @RequestParam Integer year,
        @RequestParam Long idEstablishment
    ) {
        return _scheduleService.findDaysOfMonthWithSchedules(month, year, idEstablishment);
    }

    @GetMapping
    public List<Schedule> findSchedulesOfDay(
            @RequestParam Integer day,
            @RequestParam Integer month,
            @RequestParam Integer year,
            @RequestParam Long idEstablishment
    ) {
        return _scheduleService.findSchedulesOfDay(day, month, year, idEstablishment);
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
}
