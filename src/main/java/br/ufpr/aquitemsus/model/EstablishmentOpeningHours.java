package br.ufpr.aquitemsus.model;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class EstablishmentOpeningHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private DayOfWeek dayOfWeek;

    @Column
    private LocalTime openingTime;

    @Column
    private LocalTime closingTime;
}
