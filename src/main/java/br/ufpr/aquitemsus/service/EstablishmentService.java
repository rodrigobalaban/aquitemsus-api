package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.Establishment;
import br.ufpr.aquitemsus.model.Localization;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentSimplified;
import br.ufpr.aquitemsus.repository.EstablishmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstablishmentService {

    private final EstablishmentRepository _establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        _establishmentRepository = establishmentRepository;
    }

    public List<EstablishmentSimplified> findEstablishmentsByLocalization(Localization localization, double distance) {
        return _establishmentRepository.findEstablishmentsByLocalization(
                localization.getLatitude(),
                localization.getLongitude(),
                distance
        );
    }

    public Optional<Establishment> findEstablishmentById(Long id) {
        return _establishmentRepository.findById(id);
    }
}
