package com.gainetdin.telegram.service;

import com.gainetdin.telegram.entity.MessageData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageBuilderService {

    void buildText(MessageData messageData);

    SendMessage buildMessage(MessageData messageData);
}
