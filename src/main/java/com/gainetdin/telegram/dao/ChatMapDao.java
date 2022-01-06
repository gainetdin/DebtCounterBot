package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entities.ChatEntity;
import com.gainetdin.telegram.entities.MessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatMapDao implements ChatDao {

    private final Map<Long, ChatEntity> chatMap;

    private final static Logger log = LoggerFactory.getLogger(ChatMapDao.class);

    public ChatMapDao() {
        chatMap = new HashMap<>();
    }

    @Override
    public void updateChat(MessageData messageData) {
        Long chatId = messageData.getChatId();
        if (!chatMap.containsKey(chatId)) {
            chatMap.put(chatId, new ChatEntity());
            log.info("The bot was added to a new chat (chat ID: {})", chatId);
        }
    }

    @Override
    public ChatEntity getChat(MessageData messageData) {
        return chatMap.get(messageData.getChatId());
    }
}
