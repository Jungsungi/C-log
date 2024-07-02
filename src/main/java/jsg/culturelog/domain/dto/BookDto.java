package jsg.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class BookDto {
    private String name;
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String publisher;
    private String cover;
    private String bookLink;
}
