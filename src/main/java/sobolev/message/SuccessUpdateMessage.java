package sobolev.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kirrok on 17.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessUpdateMessage {
    private boolean ok;
    @JsonProperty("result")
    private List<UpdateMessage> result;

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setResult(List<UpdateMessage> result) {
        this.result = result;
    }

    public List<UpdateMessage> getResult() {
        return result;
    }

    public boolean isOk() {
        return ok;
    }
}
