package br.ufpr.aquitemsus.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class UserSus extends User {

    @Transient
    private Localization localization;
}
