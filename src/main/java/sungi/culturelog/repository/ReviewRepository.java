package sungi.culturelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungi.culturelog.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
