package br.ufpr.aquitemsus.model;

import br.ufpr.aquitemsus.model.interfaces.SchedulePerMonth;

import java.util.List;

public class ScheduleReport {
    private Long schedulesToday;
    private Long schedulesReserved;
    private Long schedulesAttendance;
    private List<SchedulePerMonth> schedulesPerMonth;

    public Long getSchedulesToday() {
        return schedulesToday;
    }

    public void setSchedulesToday(Long schedulesToday) {
        this.schedulesToday = schedulesToday;
    }

    public Long getSchedulesReserved() {
        return schedulesReserved;
    }

    public void setSchedulesReserved(Long schedulesReserved) {
        this.schedulesReserved = schedulesReserved;
    }

    public Long getSchedulesAttendance() {
        return schedulesAttendance;
    }

    public void setSchedulesAttendance(Long schedulesAttendance) {
        this.schedulesAttendance = schedulesAttendance;
    }

    public List<SchedulePerMonth> getSchedulesPerMonth() {
        return schedulesPerMonth;
    }

    public void setSchedulesPerMonth(List<SchedulePerMonth> schedulesPerMonth) {
        this.schedulesPerMonth = schedulesPerMonth;
    }
}
