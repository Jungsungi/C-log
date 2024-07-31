package jsg.culturelog.web;

import jsg.culturelog.service.ReviewService;
import jsg.culturelog.web.interceptor.AuthorizationInterceptor;
import jsg.culturelog.web.interceptor.LoginInterceptor;
import jsg.culturelog.web.interceptor.ReviewInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    ReviewService reviewService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 여부 체크 인터셉터
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/member/add", "/member/check", "/logout",
                        "/review/book" ,"/review/book/*",
                        "/item/book/bestSeller", "/item/book" , "/item/book/*",
                        "/item/movie/**",
                        "/css/**", "/*.ico", "/error", "/js/**", "/img/**");

        // 리뷰 작성자 권한 체크 인터셉터
        registry.addInterceptor(new ReviewInterceptor(reviewService))
                .order(2)
                .addPathPatterns("/review/**")
                .excludePathPatterns("/review/book", "/review/book/*", "/review/book/add/*");

        // 사용자 정보조회 관련 권한 체크 인터셉터
        registry.addInterceptor(new AuthorizationInterceptor())
                .order(3)
                .addPathPatterns("/member/**")
                .excludePathPatterns("/member/add", "/member/check");


    }
}
