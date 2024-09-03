package sungi.culturelog.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String pubDate;
    private String isbn;
    private String publisher;
    private String bookLink;

    public Book() {
    }

    public Book(String name, String img, String description, String author, String pubDate, String isbn, String publisher, String bookLink) {
        super(name, img, description);
        this.author = author;
        this.pubDate = pubDate;
        this.isbn = isbn;
        this.publisher = publisher;
        this.bookLink = bookLink;
    }
}
