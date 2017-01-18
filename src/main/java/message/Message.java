package message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Created by kirrok on 17.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    @JsonProperty("message_id")
    private int messageId;
    private UserMessage from;
    private ChatMessage chat;
    private String date;
    private String text;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public UserMessage getFrom() {
        return from;
    }

    public void setFrom(UserMessage from) {
        this.from = from;
    }

    public ChatMessage getChat() {
        return chat;
    }

    public void setChat(ChatMessage chat) {
        this.chat = chat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
