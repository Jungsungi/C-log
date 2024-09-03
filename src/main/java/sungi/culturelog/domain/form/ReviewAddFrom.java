package sungi.culturelog.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewAddFrom {

    private String title;
    private String content;
    private Double grade;
    private Long memberId;
    private Long itemId;
}
