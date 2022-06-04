package br.ufpr.aquitemsus.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Establishment {

    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private EstablishmentCategory category;

    @Column
    private boolean alwaysOpen;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "establishment_id")
    private List<EstablishmentOpeningHours> openingHours;

    @Embedded
    private Localization localization;

    @Embedded
    private Address address;

    @Column
    private String email;

    @ElementCollection
    private List<String> phones;

    @ManyToMany
    private List<Specialty> specialties;

    @ManyToMany
    private List<Professional> professionals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EstablishmentCategory getCategory() {
        return category;
    }

    public void setCategory(EstablishmentCategory category) {
        this.category = category;
    }

    public boolean isAlwaysOpen() {
        return alwaysOpen;
    }

    public void setAlwaysOpen(boolean alwaysOpen) {
        this.alwaysOpen = alwaysOpen;
    }

    public List<EstablishmentOpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<EstablishmentOpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    public List<Professional> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<Professional> professionals) {
        this.professionals = professionals;
    }
}
