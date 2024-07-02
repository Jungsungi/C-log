package jsg.culturelog.repository;

import jsg.culturelog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    int countByUserName(String userName);

    Optional<Member> findByUserNameAndPwd(String userName, String pwd);
}
