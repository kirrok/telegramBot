package betBot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhotoSize {

    @JsonProperty("file_id")
    private String fileId;
    private long width;
    private long height;
    @JsonProperty("file_size")
    private long size;

}
