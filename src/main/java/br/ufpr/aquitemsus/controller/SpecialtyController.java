package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.Specialty;
import br.ufpr.aquitemsus.service.SpecialtyService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("specialties")
public class SpecialtyController {

    private final SpecialtyService _specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        _specialtyService = specialtyService;
    }

    @GetMapping
    public List<Specialty> findAllSpecialties(@RequestParam String search,
                                              @RequestParam int page,
                                              @RequestParam int pagesize,
                                              HttpServletResponse response) {
        Page<Specialty> pageSpecialties = this._specialtyService.findAllSpecialtiesByName(search, page, pagesize);

        response.setHeader("X-Total-Count", String.valueOf(pageSpecialties.getTotalElements()));
        return pageSpecialties.toList();
    }
}
