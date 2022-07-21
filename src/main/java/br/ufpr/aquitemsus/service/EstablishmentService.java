package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.exception.NotFoundException;
import br.ufpr.aquitemsus.model.Establishment;
import br.ufpr.aquitemsus.model.Localization;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentSimplified;
import br.ufpr.aquitemsus.repository.EstablishmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablishmentService {

    private final EstablishmentRepository _establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        _establishmentRepository = establishmentRepository;
    }

    public Page<EstablishmentSimplified> findAllEstablishmentsByName(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this._establishmentRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    public List<EstablishmentSimplified> findEstablishmentsByLocalization(Localization localization, double distance) {
        return _establishmentRepository.findEstablishmentsByLocalization(
                localization.getLatitude(),
                localization.getLongitude(),
                distance
        );
    }

    public Establishment findEstablishmentById(Long id) {
        return _establishmentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Establishment saveEstablishment(Establishment establishment) {
        return _establishmentRepository.save(establishment);
    }

    public Establishment updateEstablishment(Long id, Establishment updatedEstablishment) {
        var establishment = findEstablishmentById(id);

        establishment.setAddress(updatedEstablishment.getAddress());
        establishment.setAlwaysOpen(updatedEstablishment.isAlwaysOpen());
        establishment.setCategory(updatedEstablishment.getCategory());
        establishment.setEmail(updatedEstablishment.getEmail());
        establishment.setLocalization(updatedEstablishment.getLocalization());
        establishment.setName(updatedEstablishment.getName());
        establishment.setOpeningHours(updatedEstablishment.getOpeningHours());
        establishment.setPhone(updatedEstablishment.getPhone());
        establishment.setProfessionals(updatedEstablishment.getProfessionals());
        establishment.setSpecialties(updatedEstablishment.getSpecialties());

        return _establishmentRepository.save(establishment);
    }

    public void deleteEstablishment(Long id) {
        var establishment = findEstablishmentById(id);

        _establishmentRepository.deleteById(establishment.getId());
    }
}
