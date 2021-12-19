package com.gainetdin.telegram.data;

import java.util.List;

public class MessageData {

    private Long chatId;
    private String messageToBot;
    private String messageFromBot;
    private String userName;
    private Integer chatMemberCount;
    private List<String> newUserNames;

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

    public Integer getChatMemberCount() {
        return chatMemberCount;
    }

    public void setChatMemberCount(Integer chatMemberCount) {
        this.chatMemberCount = chatMemberCount;
    }

    public List<String> getNewUserNames() {
        return newUserNames;
    }

    public void setNewUserNames(List<String> newUserNames) {
        this.newUserNames = newUserNames;
    }
}
