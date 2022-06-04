package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.Localization;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentSimplified;
import br.ufpr.aquitemsus.service.EstablishmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/establishments")
public class EstablishmentController {

    private final EstablishmentService _establishmentService;

    public EstablishmentController(EstablishmentService establishmentService) {
        _establishmentService = establishmentService;
    }

    @GetMapping
    public List<EstablishmentSimplified> findEstablishmentsByLocalization(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("distance") double distance
    ) {
        var localization = new Localization(latitude, longitude);

        return _establishmentService.findEstablishmentsByLocalization(localization, distance);
    }
}
