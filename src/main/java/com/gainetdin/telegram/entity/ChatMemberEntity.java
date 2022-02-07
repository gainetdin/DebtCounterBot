package com.gainetdin.telegram.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "chat_members",
        uniqueConstraints = {@UniqueConstraint(name = "unique_user_in_chat", columnNames = {"userName", "chatId"})})
public class ChatMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "chatId", referencedColumnName = "telegramChatId")
    private ChatEntity chat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void changeBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }

    public ChatEntity getChat() {
        return chat;
    }

    public void setChat(ChatEntity chat) {
        this.chat = chat;
    }

    public ChatMemberEntity(String userName) {
        this.userName = userName;
        balance = BigDecimal.ZERO;
    }

    public ChatMemberEntity() {
    }

    @Override
    public String toString() {
        return "ChatMemberEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                ", chat=" + chat +
                '}';
    }
}
