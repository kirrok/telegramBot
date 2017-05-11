package sobolev.service;

import sobolev.message.ResponseMessage;
import sobolev.message.UpdateMessage;

import java.util.HashMap;
import java.util.List;

public interface RemotePointService {

    <T> ResponseMessage<T> callMethod(String methodName, HashMap<String, ?> parametersToValues);

    ResponseMessage<List<UpdateMessage>> getUpdates(Integer offset);
}
