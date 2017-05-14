package betBot.model;

import lombok.Data;

@Data
public class Entity {

    private String type;
    private int offset;
    private int length;
    private String url;

}
