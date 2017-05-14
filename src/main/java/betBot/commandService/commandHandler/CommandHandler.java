package betBot.commandService.commandHandler;

import betBot.model.Message;
import jdk.nashorn.internal.objects.annotations.Function;

@FunctionalInterface
public interface CommandHandler {

    void handle(Message command);

}
