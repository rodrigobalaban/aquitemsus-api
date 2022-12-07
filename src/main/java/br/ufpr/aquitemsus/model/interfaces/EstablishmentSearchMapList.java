package br.ufpr.aquitemsus.model.interfaces;

import br.ufpr.aquitemsus.model.Address;
import br.ufpr.aquitemsus.model.EstablishmentCategory;
import br.ufpr.aquitemsus.model.Localization;

public interface EstablishmentSearchMapList {
    Long getId();
    String getName();
    EstablishmentCategory getCategory();
    Localization getLocalization();
}
