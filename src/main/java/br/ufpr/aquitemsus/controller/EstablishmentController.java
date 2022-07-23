package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.Establishment;
import br.ufpr.aquitemsus.model.Localization;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentGridList;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentSimplified;
import br.ufpr.aquitemsus.service.EstablishmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/establishments")
public class EstablishmentController {

    private final EstablishmentService _establishmentService;

    public EstablishmentController(EstablishmentService establishmentService) {
        _establishmentService = establishmentService;
    }

    @GetMapping
    public List<EstablishmentGridList> findAllEstablishments(@RequestParam String search,
                                                               @RequestParam int page,
                                                               @RequestParam int pagesize,
                                                               HttpServletResponse response) {
        Page<EstablishmentGridList> pageEstablishments = this._establishmentService.findAllEstablishmentsByName(
                search,
                page,
                pagesize
        );

        response.setHeader("X-Total-Count", String.valueOf(pageEstablishments.getTotalElements()));
        return pageEstablishments.toList();
    }

    @GetMapping("/localization")
    public List<EstablishmentSimplified> findEstablishmentsByLocalization(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("distance") double distance
    ) {
        var localization = new Localization(latitude, longitude);
        return _establishmentService.findEstablishmentsByLocalization(localization, distance);
    }

    @GetMapping("/{id}")
    public Establishment findEstablishmentById(@PathVariable Long id) {
        return _establishmentService.findEstablishmentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Establishment saveEstablishment(@RequestBody Establishment establishment) {
        return _establishmentService.saveEstablishment(establishment);
    }

    @PutMapping("/{id}")
    public Establishment updateEstablishment(@PathVariable Long id, @RequestBody Establishment establishment) {
        return _establishmentService.updateEstablishment(id, establishment);
    }

    @DeleteMapping("/{id}")
    public void deleteEstablishment(@PathVariable Long id) {
        _establishmentService.deleteEstablishment(id);
    }
}
