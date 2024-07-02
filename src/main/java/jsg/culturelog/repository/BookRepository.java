package jsg.culturelog.repository;

import jsg.culturelog.domain.item.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = {"reviews"})
    Book findByIsbn(String isbn);
}
