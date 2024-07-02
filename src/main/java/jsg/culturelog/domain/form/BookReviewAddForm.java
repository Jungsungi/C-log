package jsg.culturelog.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookReviewAddForm {
    private String title;
    private String content;
    private Double grade;

    private String name;
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String publisher;
    private String cover;
    private String bookLink;
}
