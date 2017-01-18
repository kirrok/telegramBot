package MessageTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import sobolev.message.SuccessUpdateMessage;

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

//        System.out.println(json.toString());
//        System.out.println((new String(Files.readAllBytes(Paths.get("src/test/resources/messages/ExpectedUpdateMessage.json")))));

        assert (json.toString().equals(new String(Files.readAllBytes(Paths.get("src/test/resources/messages/ExpectedUpdateMessage.json")))));
    }
}
