package br.ufpr.aquitemsus.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Localization {

    @Column
    private double latitude;

    @Column
    private double longitude;

    public Localization() { }

    public Localization(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
