package com.gainetdin.telegram.entity;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MessageData {

    private Long chatId;
    private String messageToBot;
    private String messageFromBot;
    private String userName;
    private List<String> newUserNames;
    private String leftUserName;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessageToBot() {
        return messageToBot;
    }

    public void setMessageToBot(String messageToBot) {
        this.messageToBot = messageToBot;
    }

    public String getMessageFromBot() {
        return messageFromBot;
    }

    public void setMessageFromBot(String messageFromBot) {
        this.messageFromBot = messageFromBot;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getNewUserNames() {
        return newUserNames;
    }

    public void setNewUserNames(List<String> newUserNames) {
        this.newUserNames = newUserNames;
    }

    public String getLeftUserName() {
        return leftUserName;
    }

    public void setLeftUserName(String leftUserName) {
        this.leftUserName = leftUserName;
    }

    public boolean isEmptyMessage() {
        return messageToBot == null;
    }
}
