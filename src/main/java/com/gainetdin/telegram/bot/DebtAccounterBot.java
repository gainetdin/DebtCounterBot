package com.gainetdin.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class DebtAccounterBot extends TelegramLongPollingBot {
    Logger log = LoggerFactory.getLogger(DebtAccounterBot.class);

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageToBot = update.getMessage().getText();
        SendMessage sendSomething = new SendMessage();
        sendSomething.setChatId(String.valueOf(chatId));
        sendSomething.setText(messageToBot);
        try {
            execute(sendSomething);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
