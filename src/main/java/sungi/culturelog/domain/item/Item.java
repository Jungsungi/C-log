package sungi.culturelog.domain.item;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import sungi.culturelog.domain.Review;

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
    private String img;
    @Size(max = 2000)
    private String description;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    public Item() {
    }

    public Item(String name, String img, String description) {
        this.name = name;
        this.img = img;
        this.description = description;
    }
}
