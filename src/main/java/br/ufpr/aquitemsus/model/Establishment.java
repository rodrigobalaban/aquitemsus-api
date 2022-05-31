package br.ufpr.aquitemsus.model;

import javax.persistence.*;

@Entity
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;


}
