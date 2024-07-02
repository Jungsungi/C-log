package jsg.culturelog.service;


import jsg.culturelog.domain.Member;
import jsg.culturelog.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService service;

    @Test
    @DisplayName("멤버 저장 및 조회")
    void memberSave() throws Exception {
        //given
        Member member = new Member("member1", "1111", "name", "010-1111-2222", "M", "sungi1205@naver.com");
        //when
        Member savedMember = service.saveMember(member);
        Member findMember = service.findMemberById(savedMember.getId());

        //then
        Assertions.assertThat(savedMember.getName()).isEqualTo(findMember.getName());
    }

}