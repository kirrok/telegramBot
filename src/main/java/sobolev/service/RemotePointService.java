package sobolev.service;

import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import sobolev.message.ResponseMessage;
import sobolev.message.UpdateMessage;

import java.util.HashMap;
import java.util.List;

public interface RemotePointService {

    <T> ResponseMessage<T> callMethod(String methodName, HashMap<String, ?> parametersToValues);

    default ResponseMessage<List<UpdateMessage>> getUpdates(Integer offset) {
        return callMethod("/getUpdates", new HashMap<String, Integer>() {{
            put("offset", offset);
        }});
    }

}
