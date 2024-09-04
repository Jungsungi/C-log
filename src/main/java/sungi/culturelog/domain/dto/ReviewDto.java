package sungi.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import sungi.culturelog.domain.Review;

@Setter @Getter
public class ReviewDto {

    private Long id;
    private String title;
    private String content;
    private Double grade;
    private int hit;
    private MemberDto username;
    private ItemDto itemName;

    public ReviewDto() {

    }

    public ReviewDto(Review review) {
        id = review.getId();
        title = review.getTitle();
        content = review.getContent();
        grade = review.getGrade();
        hit = review.getHit();
        username = new MemberDto(review.getMember());
        itemName = new ItemDto(review.getItem());
    }
}
