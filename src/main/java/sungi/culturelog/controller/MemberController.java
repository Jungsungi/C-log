package sungi.culturelog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sungi.culturelog.domain.Member;
import sungi.culturelog.domain.dto.MemberDto;
import sungi.culturelog.domain.form.MemberAddForm;
import sungi.culturelog.domain.form.MemberEditForm;
import sungi.culturelog.service.MemberService;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check")
    public boolean duplicateUsernameChk(@RequestBody String username) {
        return memberService.duplicateUsername(username);
    }

    @PostMapping("/add")
    public MemberDto addMember(@RequestBody MemberAddForm form) {
        Member member = new Member(form.getUsername(), form.getPwd(), form.getName(), form.getPhone(), form.getGender(), form.getEmail());
        return memberService.saveMember(member);
    }

    @PatchMapping("/edit/{memberId}")
    public boolean editMember(@PathVariable("memberId") Long memberId, @RequestBody MemberEditForm form) {
        return memberService.editMember(memberId, form);
    }

}
