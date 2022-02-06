package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entity.ChatEntity;
import com.gainetdin.telegram.entity.MessageData;
import com.gainetdin.telegram.repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatDatabaseDao implements ChatDao {
    private final static Logger log = LoggerFactory.getLogger(ChatDatabaseDao.class);
    private final ChatRepository chatRepository;

    @Override
    public void updateChat(MessageData messageData) {
        Long chatId = messageData.getChatId();
        if (!chatRepository.existsById(chatId)) {
            chatRepository.save(new ChatEntity(chatId));
            log.info("The bot was added to a new chat (chat ID: {})", chatId);
        }
    }

    @Override
    public ChatEntity getChat(MessageData messageData) {
        return chatRepository.findById(messageData.getChatId()).orElse(null);
    }

    @Autowired
    public ChatDatabaseDao(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
}
