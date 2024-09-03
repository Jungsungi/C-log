package sungi.culturelog.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("M")
public class Movie extends Item{
    private String director;
    private String openDate;
    private String movieKey;

    public Movie() {
    }

    public Movie(String name, String img, String description, String director, String openDate, String movieKey) {
        super(name, img, description);
        this.director = director;
        this.openDate = openDate;
        this.movieKey = movieKey;
    }
}
