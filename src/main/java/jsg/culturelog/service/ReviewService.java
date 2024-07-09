package jsg.culturelog.service;

import jsg.culturelog.domain.Review;
import jsg.culturelog.domain.form.ReviewEditForm;
import jsg.culturelog.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getBookReview(Long id) {
        return reviewRepository.findFullReviewById(id);
    }

    public List<Review> getAllBookReview() {
        return reviewRepository.findAll();
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public Review changeBookReview(Long id, ReviewEditForm form) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.changeContent(form.getContent());
        review.changeGrade(form.getGrade());
        review.changeTitle(form.getTitle());

        return review;
    }
}
