package com.gainetdin.telegram.bot;

import com.gainetdin.telegram.facade.MessageUpdatingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class DebtAccounterBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(DebtAccounterBot.class);

    private final BotCredentials botCredentials;
    private final MessageUpdatingFacade facade;

    @Autowired
    public DebtAccounterBot(BotCredentials botCredentials, MessageUpdatingFacade facade) {
        this.botCredentials = botCredentials;
        this.facade = facade;
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
        SendMessage botAnswer = facade.updateChatBalance(update.getMessage());
        try {
            execute(botAnswer);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
