package sungi.culturelog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sungi.culturelog.domain.dto.ReviewDto;
import sungi.culturelog.domain.form.LoginForm;
import sungi.culturelog.service.LoginService;
import sungi.culturelog.service.ReviewService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final ReviewService reviewService;
    private final LoginService loginService;

    @GetMapping("/")
    public Map<String, List<ReviewDto>> mainReviews() {
        return reviewService.mainReviews();
    }

    @PostMapping("/api/login")
    public boolean login(@RequestBody LoginForm form, HttpServletRequest request) {
        return loginService.login(form.getUsername(), form.getPwd(), request);
    }

    @PostMapping("/api/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
