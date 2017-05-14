package betBot.service;

import betBot.model.ResponseMessage;
import betBot.model.UpdateMessage;
import betBot.model.User;

import java.util.HashMap;
import java.util.List;

public interface TelegramApiClient {

    ResponseMessage<List<UpdateMessage>> getUpdates(Integer offset);

    void sendMessage(String text, User to);

}
