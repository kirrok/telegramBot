package sobolev.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessage<T> {

    private boolean ok;
    @JsonProperty("result")
    private T result;
    private String description;
    private Integer error_code;
}
