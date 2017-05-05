package sobolev.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("message_id")
    private int messageId;
    private UserMessage from;
    private ChatMessage chat;
    private String date;
    private String text;

}
