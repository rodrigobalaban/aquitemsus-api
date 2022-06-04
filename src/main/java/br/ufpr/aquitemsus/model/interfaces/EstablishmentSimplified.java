package br.ufpr.aquitemsus.model.interfaces;

import br.ufpr.aquitemsus.model.EstablishmentCategory;
import br.ufpr.aquitemsus.model.Localization;

public interface EstablishmentSimplified {
    Long getId();
    String getName();
    EstablishmentCategory getCategory();
    boolean isAlwaysOpen();
    Localization getLocalization();
}
