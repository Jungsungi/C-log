package sungi.culturelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sungi.culturelog.domain.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r, Book b where r.item.id = b.id order by r.hit desc limit 5")
    List<Review> findMainBookReview();

    @Query("select r from Review r, Movie m where r.item.id = m.id order by r.hit desc limit 5")
    List<Review> findMainMovieReview();
}
