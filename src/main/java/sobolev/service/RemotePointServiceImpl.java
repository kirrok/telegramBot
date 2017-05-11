package sobolev.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sobolev.message.ResponseMessage;
import sobolev.message.UpdateMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class RemotePointServiceImpl implements RemotePointService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RemotePointServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> ResponseMessage<T> callMethod(String methodName, HashMap<String, ?> uriVariables) {
        return restTemplate.exchange(
            methodName,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ResponseMessage<T>>() {
            },
            uriVariables
        ).getBody();
    }

    @Override
    public ResponseMessage<List<UpdateMessage>> getUpdates(Integer lastUpdateId) {
        return restTemplate.execute("/getUpdates?offset={offset}", HttpMethod.GET, null, httpResponse -> {
                ResponseMessage<List<UpdateMessage>> response = new ResponseMessage<>();
                JsonNode jsonNode = objectMapper.readValue(httpResponse.getBody(), JsonNode.class);
                boolean ok = jsonNode.get("ok").asBoolean();
                response.setOk(ok);
                if (ok) {
                    ArrayNode result = (ArrayNode) jsonNode.get("result");
                    ArrayList<UpdateMessage> updateMessages = new ArrayList<>();
                    result.forEach(node -> updateMessages.add(objectMapper.convertValue(node, UpdateMessage.class)));
                    response.setResult(updateMessages);
                } else {
                    response.setResult(Collections.emptyList());
                    response.setError_code(jsonNode.get("error_code").asInt());
                }

                return response;
            }, new HashMap<String, Integer>() {{
                put("offset", lastUpdateId);
            }}
        );
    }
}

