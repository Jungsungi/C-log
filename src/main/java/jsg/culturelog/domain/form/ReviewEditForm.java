package jsg.culturelog.domain.form;

import jakarta.validation.constraints.NotNull;
import jsg.culturelog.domain.Review;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewEditForm {
    private Long id;
    @NotNull(message = "제목을 입력해주세요.")
    private String title;
    @NotNull(message = "리뷰 내용을 작성해주세요.")
    private String content;
    @NotNull(message = "평점을 선택해 주세요.")
    private Double grade;

    public ReviewEditForm() {

    }

    public ReviewEditForm(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.grade = review.getGrade();
    }
}
