package com.gainetdin.telegram.services;

import com.gainetdin.telegram.data.MessageData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageSendingService {

    public static SendMessage generateMessage(MessageData messageData) {
        return SendMessage.builder()
                .chatId(messageData.getChatId().toString())
                .text(messageData.getMessageFromBot())
                .build();
    }

}
