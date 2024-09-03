package sungi.culturelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungi.culturelog.domain.item.Movie;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {
    Optional<Movie> findByMovieKey(String movieKey);
}
