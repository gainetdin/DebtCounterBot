package com.gainetdin.telegram.process;

import com.gainetdin.telegram.data.ChatData;

//blank implementation of model
public class DebtProcessor {

    public static void processData(ChatData chatData) {
        chatData.setMessageFromBot("He said: " + chatData.getMessageToBot());
    }
}
