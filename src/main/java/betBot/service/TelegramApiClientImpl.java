package betBot.service;

import betBot.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TelegramApiClientImpl implements TelegramApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TelegramApiClientImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
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

    @Override
    public void sendMessage(String text, User to) {
        restTemplate.getForEntity("/sendMessage?chat_id={to}&text={text}", String.class,
            new HashMap<String, Object>() {{
                put("to", to.getId());
                put("text", text);
            }});
    }

    @Override
    public ResponseMessage<Message> sendPhoto(PhotoSize photo, User to) {
        return restTemplate.exchange("/sendPhoto?chat_id={to}&photo={photo}",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ResponseMessage<Message>>() {
            }, new HashMap<String, Object>() {{
                put("to", to.getId());
                put("photo", photo.getFileId());
            }}).getBody();
    }


}