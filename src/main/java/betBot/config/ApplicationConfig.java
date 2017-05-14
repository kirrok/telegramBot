package betBot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;
import betBot.session.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate(@Value("${bot.url}") String url) {
        System.out.println("url = " + url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new RootUriTemplateHandler(url));
        restTemplate.setInterceptors(Collections.singletonList(new ClientHttpRequestInterceptor() {
            private final Logger log = LoggerFactory.getLogger(ClientHttpRequestInterceptor.class);
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                log.info("REQUEST TO:" + httpRequest.getURI().toString());
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }

        }));
        return restTemplate;
    }
}
