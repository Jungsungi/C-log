package jsg.culturelog.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jsg.culturelog.domain.Member;
import jsg.culturelog.domain.form.MemberAddForm;
import jsg.culturelog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String memberAddForm(Model model) {
        model.addAttribute("form", new MemberAddForm());
        return "views/member/addForm";
    }

    @PostMapping("/add")
    public String memberAdd(@Validated @ModelAttribute(name = "form") MemberAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.error("error : {}", result);
            return "views/member/addForm";
        }

        log.info("회원등록 로직 온다.");

        String phoneNum = form.getPhoneFirst() + "-" + form.getPhoneMiddle() + "-" + form.getPhoneLast();

        Member member = new Member(form.getUserName(), form.getPwd(), form.getName(), phoneNum, form.getGender(), form.getEmail());
        memberService.saveMember(member);
        return "redirect:/login";
    }

    //240711 sungiJung 아이디 중복체크 url 변경
    @PostMapping("/check")
    @ResponseBody
    public int idDuplicateCheck(@RequestParam(name = "id") String id) {
        log.info("valdateId = {}", id);
        return memberService.countMemberByUserName(id);
    }

}
