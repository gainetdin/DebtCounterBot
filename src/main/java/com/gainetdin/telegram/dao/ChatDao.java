package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entities.ChatEntity;
import com.gainetdin.telegram.entities.MessageData;

public interface ChatDao {

    void updateChat(MessageData messageData);

    ChatEntity getChat(MessageData messageData);
}
