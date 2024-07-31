package jsg.culturelog.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("M")
public class Movie extends Item{
    private String director;
    private String openDate;
    @Size(max = 2000)
    private String detail;
    private String actor;
    private String movieKey;
    private Double grade;
    private String img;

    public Movie() {
    }

    public Movie(String name, String director, String openDate, String genere, String actor, String movieKey, Double grade, String img) {
        super(name);
        this.director = director;
        this.openDate = openDate;
        this.detail = genere;
        this.actor = actor;
        this.movieKey = movieKey;
        this.grade = grade;
        this.img = img;
    }
}
