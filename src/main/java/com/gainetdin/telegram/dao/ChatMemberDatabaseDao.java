package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entity.ChatEntity;
import com.gainetdin.telegram.entity.ItemsData;
import com.gainetdin.telegram.entity.MessageData;
import com.gainetdin.telegram.repository.ChatRepository;
import com.gainetdin.telegram.service.ItemsCalculatingService;
import com.gainetdin.telegram.service.MessageParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ChatMemberDatabaseDao implements ChatMemberDao {

    private final static Logger log = LoggerFactory.getLogger(ChatMemberCollectionDao.class);
    private final ChatDao chatDao;
    private final MessageParsingService messageParsingService;
    private final ItemsCalculatingService itemsCalculatingService;
    private final ChatRepository chatRepository;
    private ChatEntity chatEntity;

    @Override
    public void updateChatMember(MessageData messageData) {
        chatEntity = chatDao.getChat(messageData);
        String userName = messageData.getUserName();
        checkUsers(messageData, userName);

        if(!messageData.isEmptyMessage() && chatEntity.getChatSize() == 2) {
            log.debug("Received a message");
            ItemsData itemsData = messageParsingService.parseMessageToItems(messageData);
            BigDecimal debtChange = itemsCalculatingService.getSumOfItems(itemsData);
            if (!debtChange.equals(BigDecimal.ZERO)) {
                chatEntity.getChatMember(userName).changeBalance(debtChange);
                log.debug("Balance was changed");
            }
        }
        chatRepository.save(chatEntity);
    }

    private void checkUsers(MessageData messageData, String userName) {

        if (messageData.getNewUserNames() != null) {
            for (String newUserName : messageData.getNewUserNames()) {
                chatEntity.addChatMember(newUserName);
                log.info("Created new user by adding to chat: {}", newUserName);
            }
        }

        if (!chatEntity.hasChatMember(userName)) {
            chatEntity.addChatMember(userName);
            log.info("Created new user by his message: {}", userName);
        }

        String leftUserName = messageData.getLeftUserName();
        if (leftUserName != null) {
            chatEntity.removeChatMember(leftUserName);
            log.info("Removed user from chat: {}", leftUserName);
        }
    }

    @Autowired
    public ChatMemberDatabaseDao(@Qualifier("chatDatabaseDao") ChatDao chatDao,
                                 MessageParsingService messageParsingService,
                                 ItemsCalculatingService itemsCalculatingService,
                                 ChatRepository chatRepository) {
        this.chatDao = chatDao;
        this.messageParsingService = messageParsingService;
        this.itemsCalculatingService = itemsCalculatingService;
        this.chatRepository = chatRepository;
    }
}
