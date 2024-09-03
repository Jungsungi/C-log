package sungi.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import sungi.culturelog.domain.Member;


@Getter @Setter
public class MemberDto {
    private Long id;
    private String username;
    private String pwd;
    private String name;
    private String phone;
    private String gender;
    private String email;

    public MemberDto() {
    }

    public MemberDto(Long id, String username, String pwd, String name, String phone, String gender, String email) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
    }

    public MemberDto(Member member) {
        id = member.getId();
        username = member.getUserName();
        pwd = member.getPwd();
        name = member.getName();
        phone = member.getPhone();
        gender = member.getGender();
        email = member.getEmail();
    }
}
