package sungi.culturelog.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sungi.culturelog.domain.Member;
import sungi.culturelog.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void before() {
        Member member = new Member("sungi", "1111", "name", "010-1111-2222", "M", "sungi1205@naver.com");

        memberRepository.save(member);

    }

    @Test
    @DisplayName("duplicateName")
    void duplicateUsername() {
        String username = "sungi";
        String username2 = "hihi";
        boolean result1 = memberRepository.findByUserName(username).isEmpty();
        boolean result2 = memberRepository.findByUserName(username2).isEmpty();

        assertThat(result1).isFalse();
        assertThat(result2).isTrue();

    }
}