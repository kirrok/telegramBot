import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.io.IOException;

/**
 * Created by kirrok on 17.01.17.
 */
public class MessageParser {
    private static final Logger LOGGER = LogManager.getLogger(MessageParser.class);
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T parseMessage(String message, Class<T> clazz) {
        try {
            return mapper.readValue(message, clazz);
        } catch (Exception e) {
            LOGGER.info("Error while parsin json, {}", e);
            return null;
        }
    }
}