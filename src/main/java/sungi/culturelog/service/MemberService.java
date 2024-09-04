package sungi.culturelog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungi.culturelog.aop.annotation.Trace;
import sungi.culturelog.domain.Member;
import sungi.culturelog.domain.dto.MemberDto;
import sungi.culturelog.domain.form.MemberEditForm;
import sungi.culturelog.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Trace
    public boolean duplicateUsername(String username) {
        Optional<Member> findUser = memberRepository.findByUserName(username);
        return findUser.isEmpty();
    }

    @Transactional
    @Trace
    public MemberDto saveMember(Member member) {
        Member savedMember = memberRepository.save(member);
        return new MemberDto(savedMember);
    }

    @Transactional
    @Trace
    public boolean editMember(Long id, MemberEditForm form) {
        Member member = memberRepository.findById(id).get();
        member.updatePhone(form.getPhone());
        member.updateEmail(form.getEmail());
        member.updatePwd(form.getPwd());

        return true;
    }
}
