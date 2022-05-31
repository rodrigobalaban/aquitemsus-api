package br.ufpr.aquitemsus.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Localization {

    @Column
    private double latitude;

    @Column
    private double longitude;
}
