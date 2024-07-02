package jsg.culturelog.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String publisher;
    private String cover;
    private String bookLink;

    public Book() {
    }

    public Book(String name, String author, String pubDate, String description, String isbn, String publisher, String cover, String bookLink) {
        super(name);
        this.author = author;
        this.pubDate = pubDate;
        this.description = description;
        this.isbn = isbn;
        this.publisher = publisher;
        this.cover = cover;
        this.bookLink = bookLink;
    }
}
