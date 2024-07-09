package jsg.culturelog.domain.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookReviewAddForm {

    // 240708 validation 추가 sungiJung
    @NotNull(message = "제목을 입력해주세요.")
    private String title;
    @NotNull(message = "리뷰 내용을 작성해주세요.")
    private String content;
    @NotNull(message = "평점을 선택해 주세요.")
    private Double grade;

    private String name;
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private String publisher;
    private String cover;
    private String bookLink;
}
