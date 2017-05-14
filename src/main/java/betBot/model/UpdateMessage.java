package betBot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateMessage {

    @JsonProperty("update_id")
    private int updateId;
    private Message message;

}
