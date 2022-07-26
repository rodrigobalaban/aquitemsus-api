package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.EstablishmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstablishmentCategoryRepository extends JpaRepository<EstablishmentCategory, Long> {
    List<EstablishmentCategory> findEstablishmentCategoriesByNameContainingIgnoreCaseOrderByName(String name);
}
