package com.gainetdin.telegram.data;

import com.gainetdin.telegram.services.DebtCalculatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ChatMemberDAO {

    private static Map<String, ChatMember> chatMemberMap;

    private final static Logger log = LoggerFactory.getLogger(ChatMemberDAO.class);

    public static void updateChatMemberMap(MessageData messageData) {
        String userName = messageData.getUserName();

        if (chatMemberMap == null) {
            chatMemberMap = new HashMap<>();
        }

        if (!chatMemberMap.containsKey(userName)) {
            chatMemberMap.put(userName, new ChatMember(userName));
            log.debug("Created new user by his message: " + userName);
        }

        if (messageData.getNewUserNames() != null) {
            for (String newUserName : messageData.getNewUserNames()) {
                chatMemberMap.put(newUserName, new ChatMember(newUserName));
                log.debug("Created new user by adding to chat: " + newUserName);
            }
        }

        if (messageData.getMessageToBot() != null) {
            BigDecimal debtChange = DebtCalculatingService.analyzeData(messageData);
            if (!debtChange.equals(BigDecimal.ZERO)) {
                chatMemberMap.get(userName).changeBalance(debtChange);
            }
        }
    }

    public static void showChatBalance(MessageData messageData) {
        String userName = messageData.getUserName();
        String otherUserName = getOtherUserName(messageData);
        if (otherUserName != null) {
            BigDecimal userBalance = chatMemberMap.get(userName).getBalance();
            BigDecimal otherUserBalance = chatMemberMap.get(otherUserName).getBalance();
            BigDecimal chatBalance = userBalance.subtract(otherUserBalance);
            String whoOwesWhom = chatBalance.signum() > 0
                    ? userName + " owes " + otherUserName + ": " + chatBalance.stripTrailingZeros().toPlainString()
                    : otherUserName + " owes " + userName + ": " + chatBalance.negate().stripTrailingZeros().toPlainString();
            messageData.setMessageFromBot(whoOwesWhom);
        }
        else {
            messageData.setMessageFromBot("This chat should contain two human beings");
        }
    }

    private static String getOtherUserName(MessageData messageData) {
        String otherUserName = null;
        if (messageData.getChatMemberCount() == 3 && chatMemberMap.size() == 2) {
            for (Map.Entry<String, ChatMember> chatMemberEntry : chatMemberMap.entrySet()) {
                if (!messageData.getUserName().equals(chatMemberEntry.getKey())) {
                     otherUserName = chatMemberEntry.getKey();
                }
            }
        }
        return otherUserName;
    }
}
