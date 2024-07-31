package jsg.culturelog.repository;

import jsg.culturelog.domain.item.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByMovieKey(String movieKey);
}
