package jsg.culturelog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jsg.culturelog.domain.dto.MovieDto;
import jsg.culturelog.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/item/movie/weekBoxoffice")
    public String weekBoxoffice(Model model) {
        List<MovieDto> movieDtos = movieService.weekBoxoffice();

        model.addAttribute("movies", movieDtos);

        return "views/item/movie/weekBoxoffice";
    }

    @GetMapping("/item/movie")
    public String movieSearch(Model model, HttpServletRequest request) {

        return "views/item/movie/movieSearch";
    }

    @GetMapping("/item/movie/{movieSeq}")
    public String movieInfo(@PathVariable("movieSeq") String movieSeq, Model model) {
        MovieDto movie = movieService.getMovieInfo(movieSeq);

        return null;
    }
}
