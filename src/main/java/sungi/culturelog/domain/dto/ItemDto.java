package sungi.culturelog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import sungi.culturelog.domain.item.Item;

@Setter
@Getter
public class ItemDto {
    private Long id;
    private String name;
    private String img;
    private String description;

    public ItemDto() {
    }

    public ItemDto(Long id, String name, String img, String description) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.description = description;
    }

    public ItemDto(Item item) {
        id = item.getId();
        name = item.getName();
        img = item.getImg();
        description = item.getDescription();
    }
}
