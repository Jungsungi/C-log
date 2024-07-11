package jsg.culturelog.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jsg.culturelog.domain.Member;
import jsg.culturelog.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewInterceptor implements HandlerInterceptor {

    private final ReviewService reviewService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("Request URI : {}", requestURI);

        Long id = Long.valueOf(requestURI.substring(requestURI.lastIndexOf("/") + 1));
        Long reviewWriter = reviewService.getReviewWriter(id);

        Member login = (Member) request.getSession().getAttribute("login");

        if (reviewWriter != login.getId()) {
            log.info("권한 없는 사용자의 요청");

            response.sendRedirect("/review/book");
            return false;
        }

        return true;
    }
}
