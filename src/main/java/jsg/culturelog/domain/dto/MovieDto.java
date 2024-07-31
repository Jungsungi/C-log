package jsg.culturelog.domain.dto;

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
}
