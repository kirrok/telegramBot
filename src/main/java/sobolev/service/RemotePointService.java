package sobolev.service;

import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by kirrok on 17.01.17.
 */

public class RemotePointService {
    private final Logger LOGGER = LogManager.getLogger(RemotePointService.class);
    private final String REMOTE_URL;

    public RemotePointService(String baseUrl, String token) {
        REMOTE_URL = baseUrl + token;
    }

    public String sendGet(String methodName) throws IOException {
        return sendGet(methodName, null);
    }

    public String sendGet(String methodName, HashMap<String, String> parametersToValues) throws IOException {
        URI uri;
        try {
            final URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme("https").setHost(REMOTE_URL).setPath(methodName).build();
            if (parametersToValues != null) {
                parametersToValues.forEach(uriBuilder::setParameter);
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            LOGGER.info("Error while build uri, {}", e);
            return null;
        }
        final URL url = uri.toURL();
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        final StringBuilder response;
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String output;
            response = new StringBuilder();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
        }
        return response.toString();
    }
}