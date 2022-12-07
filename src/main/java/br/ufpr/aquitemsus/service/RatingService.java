package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.exception.NotFoundException;
import br.ufpr.aquitemsus.model.Rating;
import br.ufpr.aquitemsus.model.enums.ScheduleStatus;
import br.ufpr.aquitemsus.model.interfaces.RatingAvg;
import br.ufpr.aquitemsus.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository _ratingRepository;
    private final ScheduleService _scheduleService;


    public RatingService(RatingRepository ratingRepository, ScheduleService scheduleService) {
        _ratingRepository = ratingRepository;
        _scheduleService = scheduleService;
    }

    public Rating saveRating(Rating rating) {
        var schedule = rating.getSchedule();

        schedule.setStatus(ScheduleStatus.Rated);
        _scheduleService.saveSchedule(schedule);

        return _ratingRepository.save(rating);
    }

    public List<Rating> findRatingsByEstablishmentId(Long establishmentId) {
        return _ratingRepository.findRatingsByEstablishmentId(establishmentId);
    }

    public RatingAvg findRatingAvg(Long establishmentId) {
        var avg = this._ratingRepository.findRatingAvg(establishmentId);

        if (avg.getSchedules() == 0) {
            throw new NotFoundException();
        }

        return avg;
    }

}
