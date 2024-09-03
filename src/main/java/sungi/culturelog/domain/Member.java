package sungi.culturelog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String userName;
    private String pwd;
    private String name;
    private String phone;
    private String gender;
    private String email;

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    protected Member() {

    }

    public Member(String userName, String pwd, String name, String phone, String gender, String email) {
        this.userName = userName;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePwd(String pwd) {
        this.pwd = pwd;
    }
}
