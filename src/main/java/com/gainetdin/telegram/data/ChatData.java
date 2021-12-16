package com.gainetdin.telegram.data;

public class ChatData {

    private Long chatId;
    private String messageToBot;
    private String messageFromBot;

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
}
