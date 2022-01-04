package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entities.MessageData;

public interface ChatMemberDao {

    void updateChatMember(MessageData messageData);

    void createMessageToChat(MessageData messageData);
}
