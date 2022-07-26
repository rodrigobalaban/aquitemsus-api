package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.City;
import br.ufpr.aquitemsus.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository _cityRepository;

    public CityService(CityRepository cityRepository) {
        _cityRepository = cityRepository;
    }

    public Page<City> findAllCitiesByName(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this._cityRepository.findAllByNameContainingIgnoreCaseOrderByName(name, pageable);
    }
}
