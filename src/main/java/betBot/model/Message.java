package betBot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("message_id")
    private int messageId;
    @JsonProperty("from")
    private User user;
    private Chat chat;
    private String date;
    private String text;
    private List<Entity> entities;
    private List<PhotoSize> photo;

}
