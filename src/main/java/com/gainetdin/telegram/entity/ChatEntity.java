package com.gainetdin.telegram.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class ChatEntity {
    @Id
    private Long telegramChatId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMemberEntity> chatMembers;

    public void addChatMember(String userName) {
        if (chatMembers == null) {
            chatMembers = new ArrayList<>();
        }
        ChatMemberEntity chatMember = new ChatMemberEntity(userName);
        chatMembers.add(chatMember);
        chatMember.setChat(this);
    }

    public void removeChatMember(String userName) {
        if (getChatMember(userName) != null) {
            ChatMemberEntity chatMember = getChatMember(userName);
            chatMembers.remove(chatMember);
            chatMember.setChat(null);
        }
    }

    public ChatMemberEntity getChatMember(String userName) {
        return chatMembers.stream().filter(chatMember -> userName.equals(chatMember.getUserName()))
                .findFirst().orElse(null);
    }

    public boolean hasChatMember(String userName) {
        return chatMembers.stream().anyMatch(chatMember -> userName.equals(chatMember.getUserName()));
    }

    public int getChatSize() {
        return chatMembers.size();
    }

    public ChatMemberEntity getOtherChatMember(String userName) {
        ChatMemberEntity otherChatMember = null;
        if (getChatSize() == 2) {
            otherChatMember = chatMembers.stream().filter(chatMember -> !userName.equals(chatMember.getUserName()))
                    .findFirst().orElse(null);
        }
        return otherChatMember;
    }

    public List<ChatMemberEntity> getChatMembers() {
        return chatMembers;
    }

    public ChatEntity() {
    }

    public ChatEntity(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    @Override
    public String toString() {
        return "ChatEntity{" +
                "telegramChatId=" + telegramChatId +
                '}';
    }
}
