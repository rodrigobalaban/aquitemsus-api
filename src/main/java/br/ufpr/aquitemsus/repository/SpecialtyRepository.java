package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Page<Specialty> findAllByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}
