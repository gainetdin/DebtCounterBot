package com.gainetdin.telegram.entities;

import java.util.HashMap;
import java.util.Map;

public class ChatEntity {

    private final Map<String, ChatMemberEntity> chatMemberMap;

    public ChatEntity() {
        chatMemberMap = new HashMap<>();
    }

    public void addChatMember(String userName, ChatMemberEntity chatMember) {
        chatMemberMap.put(userName, chatMember);
    }

    public void removeChatMember(String userName) {
        chatMemberMap.remove(userName);
    }

    public ChatMemberEntity getChatMember(String userName) {
        return chatMemberMap.get(userName);
    }

    public boolean hasChatMember(String userName) {
        return chatMemberMap.containsKey(userName);
    }

    public int getChatSize() {
        return chatMemberMap.size();
    }

    public ChatMemberEntity getOtherChatMember(String userName) {
        ChatMemberEntity otherChatMember = null;
        if (getChatSize() == 2) {
            for (Map.Entry<String, ChatMemberEntity> chatMemberEntry : chatMemberMap.entrySet()) {
                if (!userName.equals(chatMemberEntry.getKey())) {
                    otherChatMember = chatMemberMap.get(chatMemberEntry.getKey());
                }
            }
        }
        return otherChatMember;
    }
}
