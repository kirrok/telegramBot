package betBot.commandService;

import betBot.commandService.commandHandler.CommandHandler;
import betBot.model.Entity;
import betBot.model.Message;
import betBot.model.UpdateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class CommandService {

    private final static String BOT_COMMAND = "bot_command";

    private final HashMap<String, CommandHandler> commandName2Handler;

    @Autowired
    public CommandService(HashMap<String, CommandHandler> commandName2Handler) {
        this.commandName2Handler = commandName2Handler;
    }

    public boolean isCommand(Message message) {
        List<Entity> entities = message.getEntities();
        return !CollectionUtils.isEmpty(entities) &&
            entities.stream().anyMatch(entity -> BOT_COMMAND.equals(entity.getType()));
    }

    public void processCommand(Message command) {
        String[] text = command.getText().split(" ");
        CommandHandler commandHandler = commandName2Handler.get(text[0]);
        if (commandHandler != null) {
            commandHandler.handle(command);
        }
    }

}
