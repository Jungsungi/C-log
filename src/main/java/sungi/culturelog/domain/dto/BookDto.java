package sungi.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import sungi.culturelog.domain.item.Book;

@Getter
@Setter
public class BookDto {

    private Long id;
    private String name;
    private String img;
    private String description;
    private String isbn;
    private String publisher;
    private String pubDate;
    private String author;
    private String bookLink;

    private Double grade;
    private int count;

    public BookDto() {
    }

    public BookDto(Book book) {
        id = book.getId();
        name = book.getName();
        img = book.getImg();
        description = book.getDescription();
        isbn = book.getIsbn();
        publisher = book.getPublisher();
        pubDate = book.getPubDate();
        author = book.getAuthor();
        bookLink = book.getBookLink();
    }

    public BookDto(String name, String img, String description, String isbn, String publisher, String pubDate, String author, String bookLink) {
        this.name = name;
        this.img = img;
        this.description = description;
        this.isbn = isbn;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.author = author;
        this.bookLink = bookLink;
    }
}
