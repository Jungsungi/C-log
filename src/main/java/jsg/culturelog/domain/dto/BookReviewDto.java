package jsg.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookReviewDto {
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String publisher;
    private String cover;
    private String bookLink;
}
