package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Establishment;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentGridList;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentSearchMapList;
import br.ufpr.aquitemsus.model.interfaces.EstablishmentSimplified;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(e.localization.latitude)) * cos(radians(e.localization.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(e.localization.latitude))))";

    @Query("SELECT e FROM Establishment e WHERE :distance > " + HAVERSINE_FORMULA)
    List<EstablishmentSimplified> findEstablishmentsByLocalization(
            final double latitude,
            final double longitude,
            final double distance
    );

    @Query("SELECT e FROM Establishment e INNER JOIN e.specialties AS s WHERE s.id IN :specialties AND :distance > " + HAVERSINE_FORMULA)
    List<EstablishmentSimplified> findEstablishmentsByLocalization(
            final double latitude,
            final double longitude,
            final double distance,
            final List<Long> specialties
    );

    Page<EstablishmentGridList> findAllByNameContainingIgnoreCaseOrderByNameAsc(String name, Pageable pageable);

    @Query(value = "SELECT e.id as id, e.name as name, e.category as category, e.address as address, e.scheduling as scheduling FROM UserAdmin u INNER JOIN u.allowedEstablishments as e WHERE u.email = :email AND UPPER(e.name) LIKE CONCAT('%',UPPER(:name),'%') ORDER BY e.name ",
      countQuery = "SELECT COUNT(e) FROM UserAdmin u INNER JOIN u.allowedEstablishments as e WHERE u.email = :email AND UPPER(e.name) LIKE CONCAT('%',UPPER(:name),'%') ORDER BY e.name ")
    Page<EstablishmentGridList> findAllByNameAndUserEmail(String name, String email, Pageable pageable);

    @Query(value = "SELECT e FROM Establishment e WHERE UPPER(e.name) LIKE CONCAT('%',UPPER(:name),'%') ORDER BY " + HAVERSINE_FORMULA,
      countQuery = "SELECT COUNT(e) FROM Establishment e WHERE UPPER(e.name) LIKE CONCAT('%',UPPER(:name),'%')")
    Page<EstablishmentSearchMapList> findAllByNameAndLocalization(
            String name,
            double latitude,
            double longitude,
            Pageable pageable);
}
