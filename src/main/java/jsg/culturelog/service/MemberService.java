package jsg.culturelog.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jsg.culturelog.domain.Member;
import jsg.culturelog.domain.form.MemberUpdateForm;
import jsg.culturelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member) {
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateForm form) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        member.updatePhone(form.getPhone());
        member.updateEmail(form.getEmail());
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public int countMemberByUserName(String username) {
        return memberRepository.countByUserName(username);
    }

    public Member findMemberByUserNameAndPwd(String loginId, String loginPwd, HttpServletRequest request) {
        Optional<Member> findMember = memberRepository.findByUserNameAndPwd(loginId, loginPwd);

        if (findMember.isEmpty()) {
            return null;
        } else {
            Member member = findMember.get();
            HttpSession session = request.getSession();
            session.setAttribute("login", member);
            return member;
        }
    }
}
