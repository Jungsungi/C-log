package sungi.culturelog.domain.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberAddForm {

    @NotBlank
    private String username;
    @NotBlank
    @Length(min = 8)
    private String pwd;
    @NotBlank
    private String name;
    private String phone;
    private String gender;
    private String email;
}
