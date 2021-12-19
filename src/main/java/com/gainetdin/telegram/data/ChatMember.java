package com.gainetdin.telegram.data;

import java.math.BigDecimal;

public class ChatMember {

    private final String userName;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void changeBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }

    public ChatMember(String userName) {
        this.userName = userName;
        balance = BigDecimal.ZERO;
    }


}
