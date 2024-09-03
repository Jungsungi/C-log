package sungi.culturelog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    @Override
//    public List<Movie> search(MovieCondition condition) {
//
//        return queryFactory
//                .select(movie)
//                .from(movie)
//                .where(queryLike(condition))
//                .fetch();
//    }
//
//    private BooleanExpression queryLike(MovieCondition condition) {
//        if (condition.getCategory().equals("total")) {
//            return movie.name.contains(condition.getQuery()).or(movie.director.contains(condition.getQuery()));
//        } else if (condition.getCategory().equals("title")) {
//            return movie.name.contains(condition.getQuery());
//        } else {
//            return movie.director.contains(condition.getQuery());
//        }
//    }
}
