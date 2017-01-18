package MessageTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import message.SuccessUpdateMessage;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kirrok on 17.01.17.
 */
public class TestUpdateMessage {

    @Test
    public void TestDeserialize() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get("src/test/resources/messages/UpdateMessage.json")));

        final ObjectMapper mapper = new ObjectMapper();
        final SuccessUpdateMessage message = mapper.readValue(jsonData, SuccessUpdateMessage.class);

        final StringWriter json = new StringWriter();
        mapper.writeValue(json, message);

        assert (json.toString().equals(new String(Files.readAllBytes(Paths.get("src/test/resources/messages/ExpectedUpdateMessage.json")))));
    }
}
