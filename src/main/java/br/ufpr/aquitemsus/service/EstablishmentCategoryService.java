package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.EstablishmentCategory;
import br.ufpr.aquitemsus.repository.EstablishmentCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablishmentCategoryService {

    private final EstablishmentCategoryRepository _establishmentCategoryRepository;

    public EstablishmentCategoryService(EstablishmentCategoryRepository establishmentCategoryRepository) {
        _establishmentCategoryRepository = establishmentCategoryRepository;
    }

    public List<EstablishmentCategory> findAllCategoriesByName(String name) {
        return _establishmentCategoryRepository.findEstablishmentCategoriesByNameContainingIgnoreCaseOrderByName(name);
    }
}
