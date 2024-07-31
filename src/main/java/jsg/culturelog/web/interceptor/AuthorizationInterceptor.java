package jsg.culturelog.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jsg.culturelog.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("Request URI : {}", requestURI);
        Long id = Long.valueOf(requestURI.substring(requestURI.lastIndexOf("/") + 1));

        HttpSession session = request.getSession();

        Member login = (Member) session.getAttribute("login");
        if (id != login.getId()) {
            log.info("권한 없는 사용자");
            response.sendRedirect("/");
            return false;
        }


        return true;
    }
}
