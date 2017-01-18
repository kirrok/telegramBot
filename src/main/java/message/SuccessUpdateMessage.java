package message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kirrok on 17.01.17.
 */

public class SuccessUpdateMessage {
    private boolean ok;
    private List<UpdateMessage> result;

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setResult(List<UpdateMessage> result) {
        this.result = result;
    }

    public List<UpdateMessage> getUpdates() {
        return result;
    }

    public boolean isOk() {
        return ok;
    }
}
