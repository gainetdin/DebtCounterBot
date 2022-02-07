package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entity.ChatEntity;
import com.gainetdin.telegram.entity.MessageData;

public interface ChatDao {

    void updateChat(MessageData messageData);

    ChatEntity getChat(MessageData messageData);
}
