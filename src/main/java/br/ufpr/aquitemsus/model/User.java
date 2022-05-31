package br.ufpr.aquitemsus.model;

import javax.persistence.*;

@Entity(name = "user_system")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;
}
