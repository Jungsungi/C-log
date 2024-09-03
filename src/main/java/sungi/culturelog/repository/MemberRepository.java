package sungi.culturelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungi.culturelog.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String username);
}
