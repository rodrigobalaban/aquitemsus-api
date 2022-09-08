package br.ufpr.aquitemsus.repository;

import br.ufpr.aquitemsus.model.Rating;
import br.ufpr.aquitemsus.model.interfaces.RatingAvg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.value) as value, COUNT(r) as schedules FROM Rating r WHERE r.schedule.establishment.id = :establishmentId")
    RatingAvg findRatingAvg(Long establishmentId);

    @Query("SELECT r FROM Rating r WHERE r.schedule.establishment.id = :establishmentId ORDER BY r.id DESC")
    List<Rating> findRatingsByEstablishmentId(Long establishmentId);
}
