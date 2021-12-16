package com.gainetdin.telegram.bot;

import com.gainetdin.telegram.data.ChatData;
import com.gainetdin.telegram.process.DebtProcessor;
import com.gainetdin.telegram.process.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
        //need to assign object creation to Spring
        ChatData chatData = new ChatData();
        chatData.setChatId(update.getMessage().getChatId());
        chatData.setMessageToBot(update.getMessage().getText());
        DebtProcessor.processData(chatData);
        try {
            execute(MessageSender.generateMessage(chatData));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
