package sungi.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieDto {
    private String moviename;
    private String movieCode;
    private String openDate;
    private String img;
    private String nation;
    private String director;
    private String actors;
    private String detail;

    public MovieDto() {
    }

    public MovieDto(String moviename, String movieCode, String openDate, String img, String director, String detail) {
        this.moviename = moviename;
        this.movieCode = movieCode;
        this.openDate = openDate;
        this.img = img;
        this.director = director;
        this.detail = detail;
    }
}
