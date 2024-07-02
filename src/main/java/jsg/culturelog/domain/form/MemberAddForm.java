package jsg.culturelog.domain.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAddForm {
    @NotNull(message = "아이디를 입력해주세요.")
    private String userName;
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String pwd;
    @NotNull(message = "비밀번호 확인을 입력해주세요.")
    private String pwdCheck;
    @NotNull(message = "email 주소를 입력해주세요.")
    private String email;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    private String phoneFirst;
    private String phoneMiddle;
    private String phoneLast;
    @NotNull(message = "성별을 선택해주세요.")
    private String gender;
}
