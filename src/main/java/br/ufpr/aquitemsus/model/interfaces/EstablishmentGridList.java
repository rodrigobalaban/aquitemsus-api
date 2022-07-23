package br.ufpr.aquitemsus.model.interfaces;

import br.ufpr.aquitemsus.model.Address;
import br.ufpr.aquitemsus.model.EstablishmentCategory;
import br.ufpr.aquitemsus.model.Localization;

public interface EstablishmentGridList {
    Long getId();
    String getName();
    EstablishmentCategory getCategory();
    Address getAddress();
}
