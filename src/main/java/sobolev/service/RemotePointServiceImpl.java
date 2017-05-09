package sobolev.service;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sobolev.message.ResponseMessage;
import sobolev.message.UpdateMessage;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;

@Service
public class RemotePointServiceImpl implements RemotePointService {

    private final RestTemplate restTemplate;

    @Autowired
    public RemotePointServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> ResponseMessage<T> callMethod(String methodName, HashMap<String, ?> uriVariables) {
        return restTemplate.exchange(
            methodName,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ResponseMessage<T>>() {},
            uriVariables
        ).getBody();
    }
}