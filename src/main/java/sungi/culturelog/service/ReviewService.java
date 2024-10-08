package sungi.culturelog.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungi.culturelog.aop.annotation.Trace;
import sungi.culturelog.domain.Member;
import sungi.culturelog.domain.Review;
import sungi.culturelog.domain.dto.ReviewDto;
import sungi.culturelog.domain.form.ReviewAddFrom;
import sungi.culturelog.domain.form.ReviewEditFrom;
import sungi.culturelog.domain.item.Item;
import sungi.culturelog.repository.ItemRepository;
import sungi.culturelog.repository.ReviewRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Trace
    public void saveReview(ReviewAddFrom form, HttpSession session) {
        Member login = (Member) session.getAttribute("login");
        Item item = itemRepository.findById(form.getItemId()).get();
        Review review = new Review(form.getTitle(), form.getContent(), form.getGrade(), login, item);

        reviewRepository.save(review);
    }

    @Transactional
    @Trace
    public ReviewDto reviewInfo(Long reviewId) {
        Optional<Review> findReview = reviewRepository.findById(reviewId);
        if (findReview.isPresent()) {
            findReview.get().hitUp();
            ReviewDto review = new ReviewDto(findReview.get());
            return review;
        } else {
            throw new IllegalStateException("등록되지 않은 리뷰");
        }
    }

    public List<ReviewDto> search() {
        return null;
    }

    @Trace
    @Transactional
    public void editReview(Long reviewId, ReviewEditFrom form) {
        Optional<Review> findReview = reviewRepository.findById(reviewId);
        if (findReview.isPresent()) {
            Review review = findReview.get();
            review.changeContent(form.getContent());
            review.changeTitle(form.getTitle());
            review.changeGrade(form.getGrade());
        } else {
            throw new IllegalStateException("없는 리뷰입니다.");
        }
    }

    @Trace
    public Map<String, List<ReviewDto>> mainReviews() {
        List<Review> findBookReviews = reviewRepository.findMainBookReview();
        List<Review> findMovieReviews = reviewRepository.findMainMovieReview();

        List<ReviewDto> bookReview = findBookReviews.stream().map(review -> new ReviewDto(review)).toList();
        List<ReviewDto> movieReview = findMovieReviews.stream().map(review -> new ReviewDto(review)).toList();

        Map<String, List<ReviewDto>> reviews = new HashMap<>();
        reviews.put("bookReview", bookReview);
        reviews.put("movieReview", movieReview);

        return reviews;
    }

    public Long getReviewWriter(Long id) {
        return null;
    }
}
