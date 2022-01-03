package com.gainetdin.telegram.bot;

import com.gainetdin.telegram.dao.ChatMemberDAO;
import com.gainetdin.telegram.data.MessageData;
import com.gainetdin.telegram.services.MessageSendingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class DebtAccounterBot extends TelegramLongPollingBot {
    Logger log = LoggerFactory.getLogger(DebtAccounterBot.class);

    BotCredentials botCredentials;

    @Autowired
    public DebtAccounterBot(BotCredentials botCredentials) {
        this.botCredentials = botCredentials;
    }


    @Override
    public String getBotUsername() {
        return botCredentials.getName();
    }

    @Override
    public String getBotToken() {
        return botCredentials.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        MessageData messageData = createMessageData(update.getMessage());
        ChatMemberDAO.updateChatMemberMap(messageData);
        ChatMemberDAO.showChatBalance(messageData);
        try {
            execute(MessageSendingService.generateMessage(messageData));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private MessageData createMessageData(Message message) {
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

        GetChatMemberCount getChatMemberCount = new GetChatMemberCount();
        getChatMemberCount.setChatId(message.getChatId().toString());
        try {
            messageData.setChatMemberCount(execute(getChatMemberCount));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        return messageData;
    }
}
