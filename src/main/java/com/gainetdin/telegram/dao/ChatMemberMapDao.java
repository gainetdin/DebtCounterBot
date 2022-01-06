package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entities.ChatEntity;
import com.gainetdin.telegram.entities.ChatMemberEntity;
import com.gainetdin.telegram.entities.ItemsData;
import com.gainetdin.telegram.entities.MessageData;
import com.gainetdin.telegram.services.ItemsCalculatingService;
import com.gainetdin.telegram.services.MessageParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ChatMemberMapDao implements ChatMemberDao {

    private ChatEntity chatEntity;
    private final ChatDao chatDao;
    private final static Logger log = LoggerFactory.getLogger(ChatMemberMapDao.class);

    private final MessageParsingService messageParsingService;
    private final ItemsCalculatingService itemsCalculatingService;

    @Override
    public void updateChatMember(MessageData messageData) {
        chatEntity = chatDao.getChat(messageData);
        String userName = messageData.getUserName();
        checkUsers(messageData, userName);

        if (!messageData.isEmptyMessage() && chatEntity.getChatSize() == 2) {
            log.debug("Received a message");
            ItemsData itemsData = messageParsingService.parseMessageToItems(messageData);
            BigDecimal debtChange = itemsCalculatingService.getSumOfItems(itemsData);
            if (!debtChange.equals(BigDecimal.ZERO)) {
                chatEntity.getChatMember(userName).changeBalance(debtChange);
                log.debug("Balance was changed");
            }
        }
    }

    private void checkUsers(MessageData messageData, String userName) {

        if (!chatEntity.hasChatMember(userName)) {
            chatEntity.addChatMember(userName, new ChatMemberEntity(userName));
            log.info("Created new user by his message: {}", userName);
        }

        if (messageData.getNewUserNames() != null) {
            for (String newUserName : messageData.getNewUserNames()) {
                chatEntity.addChatMember(newUserName, new ChatMemberEntity(newUserName));
                log.info("Created new user by adding to chat: {}", newUserName);
            }
        }

        String leftUserName = messageData.getLeftUserName();
        if (leftUserName != null) {
            chatEntity.removeChatMember(leftUserName);
            log.info("Removed user from chat: {}", leftUserName);
        }
    }

    @Autowired
    public ChatMemberMapDao(ChatDao chatDao,
                            MessageParsingService messageParsingService,
                            ItemsCalculatingService itemsCalculatingService) {
        this.chatDao = chatDao;
        this.messageParsingService = messageParsingService;
        this.itemsCalculatingService = itemsCalculatingService;
    }
}
