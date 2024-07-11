package jsg.culturelog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jsg.culturelog.domain.Member;
import jsg.culturelog.domain.form.LoginForm;
import jsg.culturelog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute(name = "form") LoginForm form) {
        return "views/login/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute(name = "form") LoginForm form, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            log.error("error : {}", result);
            return "views/login/login";
        }

        String loginId = form.getLoginId();
        String loginPwd = form.getLoginPwd();

        Member loginUser = memberService.findMemberByUserNameAndPwd(loginId, loginPwd, request);

        if (loginUser == null) {
            result.reject("noUser", "아이디와 비밀번호를 다시 확인해주세요.");
            return "views/login/login";
        }

        return "redirect:/";
    }

    //240711 sungiJung 로그아웃 동작 변경
    @PostMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            session.invalidate();
        }

        return "success";
    }
}
