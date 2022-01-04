package com.gainetdin.telegram.facade;

import com.gainetdin.telegram.dao.ChatMemberDao;
import com.gainetdin.telegram.entities.MessageData;
import com.gainetdin.telegram.services.MessageBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageUpdatingFacade {

    private final ChatMemberDao chatMemberDao;
    private final MessageBuilderService messageBuilderService;

    @Autowired
    public MessageUpdatingFacade(ChatMemberDao chatMemberDao, MessageBuilderService messageBuilderService) {
        this.chatMemberDao = chatMemberDao;
        this.messageBuilderService = messageBuilderService;
    }

    public SendMessage updateChatBalance(Message message) {
        MessageData messageData = convertToMessageData(message);
        chatMemberDao.updateChatMember(messageData);
        chatMemberDao.createMessageToChat(messageData);
        return messageBuilderService.buildMessage(messageData);
    }

    private MessageData convertToMessageData(Message message) {
        MessageData messageData = new MessageData();
        messageData.setChatId(message.getChatId());
        messageData.setMessageToBot(message.getText());
        messageData.setUserName(message.getFrom().getUserName());

        List<User> newUsersList = message.getNewChatMembers();
        if (!newUsersList.isEmpty()) {
            List<String> newUserNamesList = new ArrayList<>();
            for (User newUser : newUsersList) {
                if (!newUser.getIsBot()) {
                    newUserNamesList.add(newUser.getUserName());
                }
            }
            messageData.setNewUserNames(newUserNamesList);
        }

        return messageData;
    }
}
