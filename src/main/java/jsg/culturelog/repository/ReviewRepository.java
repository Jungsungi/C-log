package jsg.culturelog.repository;

import jsg.culturelog.domain.Member;
import jsg.culturelog.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member", "item"})
    Review findFullReviewById(Long id);

    @Override
    @EntityGraph(attributePaths = {"member", "item"})
    List<Review> findAll();

    //240711 sungiJung 리뷰 작성자 찾는 메소드 추가
    @EntityGraph(attributePaths = {"member"})
    Review findMemberById(Long id);
}
