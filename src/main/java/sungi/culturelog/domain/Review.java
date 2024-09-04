package sungi.culturelog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import sungi.culturelog.domain.item.Item;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    @Size(max = 2000)
    private String content;
    private Double grade;

    // 240904 조회수 추가
    private int hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;

    public Review() {
    }

    public Review(String title, String content, Double grade, Member member, Item item) {
        this.title = title;
        this.content = content;
        this.grade = grade;
        this.member = member;
        this.item = item;
    }

    /**
     * 240708 sungijung
     * review 수정 메소드 추가
     */
    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeGrade(Double grade) {
        this.grade = grade;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void hitUp() {
        this.hit += 1;
    }
}
