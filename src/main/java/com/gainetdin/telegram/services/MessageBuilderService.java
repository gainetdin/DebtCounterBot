package com.gainetdin.telegram.services;

import com.gainetdin.telegram.entities.MessageData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageBuilderService {

    void buildText(MessageData messageData);

    SendMessage buildMessage(MessageData messageData);
}
