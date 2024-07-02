package jsg.culturelog.domain.item;

import jakarta.persistence.*;
import jsg.culturelog.domain.Review;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }
}
