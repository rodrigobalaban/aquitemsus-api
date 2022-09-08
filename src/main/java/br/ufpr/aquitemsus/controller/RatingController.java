package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.Rating;
import br.ufpr.aquitemsus.model.interfaces.RatingAvg;
import br.ufpr.aquitemsus.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rating")
public class RatingController {

    private final RatingService _ratingService;

    public RatingController(RatingService ratingService) {
        _ratingService = ratingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rating saveRating(@RequestBody Rating rating) {
        return _ratingService.saveRating(rating);
    }

    @GetMapping
    public List<Rating> findRatings(@RequestParam("establishmentId") Long establishmentId) {
        return _ratingService.findRatingsByEstablishmentId(establishmentId);
    }

    @GetMapping("avg")
    public RatingAvg findRatingAvg(@RequestParam("establishmentId") Long establishmentId) {
        return _ratingService.findRatingAvg(establishmentId);
    }
}
