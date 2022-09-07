package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
