package com.gainetdin.telegram.services;

import com.gainetdin.telegram.dao.ChatDao;
import com.gainetdin.telegram.entities.ChatEntity;
import com.gainetdin.telegram.entities.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;

@Service
public class MessageBuilderServiceImpl implements MessageBuilderService {

    private final ChatDao chatDao;

    @Override
    public void buildText(MessageData messageData) {
        ChatEntity chatEntity = chatDao.getChat(messageData);

        if (messageData.isEmptyMessage()) {
            messageData.setMessageFromBot("");
            return;
        }

        if (chatEntity.getChatSize() == 2) {
            String userName = messageData.getUserName();
            String otherUserName = chatEntity.getOtherChatMember(userName).getUserName();
            BigDecimal userBalance = chatEntity.getChatMember(userName).getBalance();
            BigDecimal otherUserBalance = chatEntity.getChatMember(otherUserName).getBalance();
            BigDecimal chatBalance = userBalance.subtract(otherUserBalance);
            String debtor, creditor;
            if (chatBalance.signum() > 0) {
                debtor = userName;
                creditor = otherUserName;
            }
            else {
                debtor = otherUserName;
                creditor = userName;
            }
            String balanceFormatted = chatBalance.stripTrailingZeros().abs().toPlainString();
            String whoOwesWhom = String.format("%s owes %s: %s", debtor, creditor, balanceFormatted);
            messageData.setMessageFromBot(whoOwesWhom);
        }
        else if (chatEntity.getChatSize() > 2) {
            messageData.setMessageFromBot("Too many chat members.\n" +
                    "I know how to calculate debt only between two people");
        }
        else {
            messageData.setMessageFromBot("Add another person to this chat.\n" +
                    "If you've done this before, another chat member should write something");
        }
    }

    @Override
    public SendMessage buildMessage(MessageData messageData) {
        return SendMessage.builder()
                .chatId(messageData.getChatId().toString())
                .text(messageData.getMessageFromBot())
                .build();
    }

    @Autowired
    public MessageBuilderServiceImpl(ChatDao chatDao) {
        this.chatDao = chatDao;
    }
}
