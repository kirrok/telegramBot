package betBot.service;

import betBot.model.*;

import java.util.HashMap;
import java.util.List;

public interface TelegramApiClient {

    ResponseMessage<List<UpdateMessage>> getUpdates(Integer offset);

    void sendMessage(String text, User to);

    ResponseMessage<Message> sendPhoto(PhotoSize photo, User to);
}
