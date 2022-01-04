package com.gainetdin.telegram.dao;

import com.gainetdin.telegram.entities.ChatMember;
import com.gainetdin.telegram.entities.ItemsData;
import com.gainetdin.telegram.entities.MessageData;
import com.gainetdin.telegram.services.ItemsCalculatingService;
import com.gainetdin.telegram.services.MessageParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatMemberDaoMapImpl implements ChatMemberDao {

    private Map<String, ChatMember> chatMemberMap;
    private boolean isNotEmptyMessage;
    private boolean isTwoMemberChat;
    private final static Logger log = LoggerFactory.getLogger(ChatMemberDaoMapImpl.class);

    private final MessageParsingService messageParsingService;
    private final ItemsCalculatingService itemsCalculatingService;

    @Override
    public void updateChatMember(MessageData messageData) {
        String userName = messageData.getUserName();
        checkNewUsers(messageData, userName);
        isNotEmptyMessage = checkMessageIsNotEmpty(messageData);
        isTwoMemberChat = checkChatHasTwoMembers();

        if (isTwoMemberChat && isNotEmptyMessage) {
            log.debug("Received a message");
            ItemsData itemsData = messageParsingService.parseMessageToItems(messageData);
            BigDecimal debtChange = itemsCalculatingService.getSumOfItems(itemsData);
            if (!debtChange.equals(BigDecimal.ZERO)) {
                chatMemberMap.get(userName).changeBalance(debtChange);
                log.debug("Balance was changed");
            }
        }
    }

    private boolean checkMessageIsNotEmpty(MessageData messageData) {
        return messageData.getMessageToBot() != null;
    }

    private boolean checkChatHasTwoMembers() {
        return chatMemberMap.size() == 2;
    }

    private void checkNewUsers(MessageData messageData, String userName) {
        if (chatMemberMap == null) {
            chatMemberMap = new HashMap<>();
        }

        if (!chatMemberMap.containsKey(userName)) {
            chatMemberMap.put(userName, new ChatMember(userName));
            log.debug("Created new user by his message: {}", userName);
        }

        if (messageData.getNewUserNames() != null) {
            for (String newUserName : messageData.getNewUserNames()) {
                chatMemberMap.put(newUserName, new ChatMember(newUserName));
                log.debug("Created new user by adding to chat: {}", newUserName);
            }
        }
    }

    @Override
    public void createMessageToChat(MessageData messageData) {
        String userName = messageData.getUserName();
        String otherUserName = getOtherUserName(messageData);
        if (!isNotEmptyMessage) {
            messageData.setMessageFromBot("");
        }
        else if (isTwoMemberChat) {
            BigDecimal userBalance = chatMemberMap.get(userName).getBalance();
            BigDecimal otherUserBalance = chatMemberMap.get(otherUserName).getBalance();
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
            String whoOwesWhom = String.format("@%s owes @%s: %s", debtor, creditor, balanceFormatted);
            messageData.setMessageFromBot(whoOwesWhom);
        }
        else if (chatMemberMap.size() > 2) {
            messageData.setMessageFromBot("Too many chat members." +
                    "I know how to calculate debt only between two people");
        }
        else {
            messageData.setMessageFromBot("Add another person to this chat.\n" +
                    "If you've done this before, another chat member should write something");
        }
    }

    private String getOtherUserName(MessageData messageData) {
        String otherUserName = null;
        if (checkChatHasTwoMembers()) {
            for (Map.Entry<String, ChatMember> chatMemberEntry : chatMemberMap.entrySet()) {
                if (!messageData.getUserName().equals(chatMemberEntry.getKey())) {
                     otherUserName = chatMemberEntry.getKey();
                }
            }
        }
        return otherUserName;
    }

    @Autowired
    public ChatMemberDaoMapImpl(MessageParsingService messageParsingService,
                                ItemsCalculatingService itemsCalculatingService) {
        this.messageParsingService = messageParsingService;
        this.itemsCalculatingService = itemsCalculatingService;
    }
}
