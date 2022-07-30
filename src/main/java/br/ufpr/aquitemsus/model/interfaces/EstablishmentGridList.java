package br.ufpr.aquitemsus.model.interfaces;

import br.ufpr.aquitemsus.model.Address;
import br.ufpr.aquitemsus.model.EstablishmentCategory;

public interface EstablishmentGridList {
    Long getId();
    String getName();
    EstablishmentCategory getCategory();
    Address getAddress();
    boolean isScheduling();
}
