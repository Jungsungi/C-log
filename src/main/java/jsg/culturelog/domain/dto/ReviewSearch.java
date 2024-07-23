package jsg.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Review 검색용 객체
 */

@Getter @Setter
public class ReviewSearch {
    private String userName;
    private String page;
    private String title;
    private String name;
}
