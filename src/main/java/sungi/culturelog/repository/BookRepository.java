package sungi.culturelog.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sungi.culturelog.domain.item.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = {"reviews"})
    Optional<Book> findByIsbn(String isbn);
}
