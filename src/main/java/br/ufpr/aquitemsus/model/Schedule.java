package br.ufpr.aquitemsus.model;

import br.ufpr.aquitemsus.model.enums.ScheduleStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Establishment establishment;

    @Column
    private Date date;

    @Column
    private ScheduleStatus status;

    @ManyToOne
    private UserSus userSus;

    @ManyToOne
    private Professional professional;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public UserSus getUserSus() {
        return userSus;
    }

    public void setUserSus(UserSus userSus) {
        this.userSus = userSus;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }
}
