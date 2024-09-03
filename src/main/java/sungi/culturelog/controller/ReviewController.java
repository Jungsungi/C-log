package sungi.culturelog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sungi.culturelog.domain.dto.ReviewDto;
import sungi.culturelog.domain.form.ReviewAddFrom;
import sungi.culturelog.domain.form.ReviewEditFrom;
import sungi.culturelog.service.ReviewService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public List<ReviewDto> searchReview() {
        return reviewService.search();
    }

    @GetMapping("/{reviewId}")
    public ReviewDto getReview(@PathVariable("reviewId") Long reviewId) {
        return reviewService.reviewInfo(reviewId);
    }

    @PostMapping("/add")
    public boolean addReview(@RequestBody ReviewAddFrom form, HttpSession session) {
        reviewService.saveReview(form, session);
        return true;
    }

    @PatchMapping("/edit/{reviewId}")
    public boolean editReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewEditFrom form) {
        reviewService.editReview(reviewId, form);
        return true;
    }
}
