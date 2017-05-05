package sobolev.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatMessage {

    private int id;
    private String type;
    private String title;
    private String username;
    @JsonProperty("first_name")
    private String firstname;
    @JsonProperty("last_name")
    private String lastName;
    private boolean all_members_are_administrators;

}
