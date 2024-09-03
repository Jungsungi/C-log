package sungi.culturelog.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberEditForm {

    private Long id;
    private String pwd;
    private String phone;
    private String email;
}
