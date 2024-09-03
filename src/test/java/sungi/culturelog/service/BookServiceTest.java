package sungi.culturelog.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sungi.culturelog.domain.dto.BookDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    @DisplayName("bestSeller")
    void bestSellerTest() {
        List<BookDto> bestSeller = bookService.getBestSeller();

        assertThat(bestSeller).isNotEmpty();
        assertThat(bestSeller.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("findByIsbn")
    void selectByIsbn() {
        String isbn = "9791167374561";
        BookDto bookDto = bookService.getBookInfo(isbn);

        assertThat(bookDto.getIsbn()).isEqualTo(isbn);
        assertThat(bookDto.getName()).isEqualTo("영원한 천국");
    }
}