package sungi.culturelog.web.condition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchCondition {
    private String queryType;
    private String query;
    private String page;
}
