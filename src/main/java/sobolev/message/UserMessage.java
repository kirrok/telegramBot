package sobolev.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserMessage {

    private int id;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "last_name")
    private String lastName;
    private String username;

}
