package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Establishment;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentGridList;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentSimplified;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(e.localization.latitude)) * cos(radians(e.localization.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(e.localization.latitude))))";

    @Query("SELECT e FROM Establishment e WHERE :distance > " + HAVERSINE_FORMULA)
    List<EstablishmentSimplified> findEstablishmentsByLocalization(
            @Param("latitude") final double latitude,
            @Param("longitude") final double longitude,
            @Param("distance") final double distance
    );

    Page<EstablishmentGridList> findAllByNameContainingIgnoreCaseOrderByNameAsc(String name, Pageable pageable);
}
