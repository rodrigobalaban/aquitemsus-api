package br.ufpr.aquitemsus.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class UserAdmin extends User {

    @ManyToMany
    private List<Establishment> allowedEstablishments;

    public List<Establishment> getAllowedEstablishments() {
        return allowedEstablishments;
    }

    public void setAllowedEstablishments(List<Establishment> allowedEstablishments) {
        this.allowedEstablishments = allowedEstablishments;
    }
}
