package com.gainetdin.telegram.facade;

import com.gainetdin.telegram.dao.ChatDao;
import com.gainetdin.telegram.dao.ChatMemberDao;
import com.gainetdin.telegram.entity.MessageData;
import com.gainetdin.telegram.service.MessageBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageUpdatingFacade {

    private final ChatDao chatDao;
    private final ChatMemberDao chatMemberDao;
    private final MessageBuilderService messageBuilderService;

    @Autowired
    public MessageUpdatingFacade(@Qualifier("chatDatabaseDao") ChatDao chatDao,
                                 @Qualifier("chatMemberDatabaseDao") ChatMemberDao chatMemberDao,
                                 MessageBuilderService messageBuilderService) {
        this.chatDao = chatDao;
        this.chatMemberDao = chatMemberDao;
        this.messageBuilderService = messageBuilderService;
    }

    public SendMessage updateChatBalance(Message message) {
        MessageData messageData = convertToMessageData(message);
        chatDao.updateChat(messageData);
        chatMemberDao.updateChatMember(messageData);
        messageBuilderService.buildText(messageData);
        return messageBuilderService.buildMessage(messageData);
    }

    private MessageData convertToMessageData(Message message) {
        MessageData messageData = new MessageData();
        messageData.setChatId(message.getChatId());
        messageData.setMessageToBot(message.getText());
        messageData.setUserName(message.getFrom().getUserName());
        messageData.setLeftUserName(getLeftUserName(message));
        messageData.setNewUserNames(getNewUserNamesList(message));
        return messageData;
    }

    private List<String> getNewUserNamesList(Message message) {
        List<User> newUsersList = message.getNewChatMembers();
        List<String> newUserNamesList = new ArrayList<>();
        if (!newUsersList.isEmpty()) {
            for (User newUser : newUsersList) {
                if (!newUser.getIsBot()) {
                    newUserNamesList.add(newUser.getUserName());
                }
            }
        }
        return newUserNamesList;
    }

    private String getLeftUserName(Message message) {
        User leftUser = message.getLeftChatMember();
        String leftUserName = null;
        if(leftUser != null && !leftUser.getIsBot()) {
            leftUserName = leftUser.getUserName();
        }
        return leftUserName;
    }
}
