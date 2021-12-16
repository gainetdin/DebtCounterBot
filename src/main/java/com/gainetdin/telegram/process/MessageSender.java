package com.gainetdin.telegram.process;

import com.gainetdin.telegram.data.ChatData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageSender {
    public static SendMessage generateMessage(ChatData chatData) {
        return SendMessage.builder()
                .chatId(chatData.getChatId().toString())
                .text(chatData.getMessageFromBot())
                .build();
    }
}
