package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.Rating;
import br.ufpr.aquitemsus.model.enums.ScheduleStatus;
import br.ufpr.aquitemsus.repository.RatingRepository;
import org.springframework.stereotype.Service;

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
}
