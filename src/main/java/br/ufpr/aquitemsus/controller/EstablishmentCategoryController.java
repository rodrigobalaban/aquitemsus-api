package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.EstablishmentCategory;
import br.ufpr.aquitemsus.service.EstablishmentCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/establishments-categories")
public class EstablishmentCategoryController {

    private final EstablishmentCategoryService _establishmentCategoryService;

    public EstablishmentCategoryController(EstablishmentCategoryService establishmentCategoryService) {
        _establishmentCategoryService = establishmentCategoryService;
    }

    @GetMapping
    public List<EstablishmentCategory> findAllCategories(Optional<String> name) {
        return _establishmentCategoryService.findAllCategoriesByName(name.orElse(""));
    }
}
