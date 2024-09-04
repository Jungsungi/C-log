package sungi.culturelog.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungi.culturelog.domain.Member;
import sungi.culturelog.repository.MemberRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public boolean login(String username, String pwd, HttpServletRequest request) {
        Optional<Member> result = memberRepository.findByUserNameAndPwd(username, pwd);
        if (result.isPresent()) {
            request.getSession().setAttribute("login", result.get());
            return true;
        }
        return false;
    }
}
