package sungi.culturelog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sungi.culturelog.domain.dto.BookDto;
import sungi.culturelog.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/item/books/bestSeller")
    public List<BookDto> bestSeller() {
        return bookService.getBestSeller();
    }

    @GetMapping("/api/item/books/{isbn}")
    public BookDto bookInfo(@PathVariable("isbn") String isbn) {
        return bookService.getBook(isbn);
    }
}
