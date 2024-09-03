package sungi.culturelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungi.culturelog.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
