package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Page<City> findAllByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}
