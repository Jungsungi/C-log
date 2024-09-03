package sungi.culturelog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sungi.culturelog.domain.dto.MovieDto;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    @GetMapping("/api/item/movies/boxoffice")
    public List<MovieDto> boxOffice() {
        return null;
    }

    @GetMapping("/api/item/movies/{movieId}")
    public MovieDto movieInfo(@PathVariable("movieId") Long movieId) {
        return null;
    }

    @GetMapping("/api/item/movies")
    public List<MovieDto> searchMovie() {
        return null;
    }
}
