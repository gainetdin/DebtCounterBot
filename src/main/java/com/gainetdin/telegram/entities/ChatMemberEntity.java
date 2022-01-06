package com.gainetdin.telegram.entities;

import java.math.BigDecimal;

public class ChatMemberEntity {

    private final String userName;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public String getUserName() {
        return userName;
    }

    public void changeBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }

    public ChatMemberEntity(String userName) {
        this.userName = userName;
        balance = BigDecimal.ZERO;
    }
}
