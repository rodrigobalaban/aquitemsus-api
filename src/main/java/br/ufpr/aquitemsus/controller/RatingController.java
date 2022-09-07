package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.Rating;
import br.ufpr.aquitemsus.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
