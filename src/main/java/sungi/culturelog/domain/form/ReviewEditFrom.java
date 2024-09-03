package sungi.culturelog.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewEditFrom {
    private Long id;
    private String content;
    private String title;
    private Double grade;
}
