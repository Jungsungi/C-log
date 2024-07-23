package jsg.culturelog.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("M")
public class Movie extends Item{
    private String director;
    private String openDate;
    private String genere;
    private String actor;
    private String movieKey;
    private String grade;
}
