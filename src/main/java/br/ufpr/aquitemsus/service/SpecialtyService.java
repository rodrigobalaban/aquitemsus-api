package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.Specialty;
import br.ufpr.aquitemsus.repository.SpecialtyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyService {

    private final SpecialtyRepository _specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        _specialtyRepository = specialtyRepository;
    }

    public Page<Specialty> findAllSpecialtiesByName(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this._specialtyRepository.findAllByNameContainingIgnoreCaseOrderByName(name, pageable);
    }
}
