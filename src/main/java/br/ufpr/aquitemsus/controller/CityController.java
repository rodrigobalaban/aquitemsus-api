package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.City;
import br.ufpr.aquitemsus.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("cities")
public class CityController {

    private final CityService _cityService;

    public CityController(CityService cityService) {
        _cityService = cityService;
    }

    @GetMapping
    public List<City> findAllCities(@RequestParam String search,
                                    @RequestParam int page,
                                    @RequestParam int pagesize,
                                    HttpServletResponse response) {
        Page<City> pageCities = this._cityService.findAllCitiesByName(search, page, pagesize);

        response.setHeader("X-Total-Count", String.valueOf(pageCities.getTotalElements()));
        return pageCities.toList();
    }
}
