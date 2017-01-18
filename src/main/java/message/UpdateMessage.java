package message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kirrok on 17.01.17.
 */
public class UpdateMessage {
    @JsonProperty("update_id")
    private int updateId;
    private Message message;

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getUpdateId() {
        return updateId;
    }

    public Message getMessage() {
        return message;
    }

}
