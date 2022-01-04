package com.gainetdin.telegram.services;

import com.gainetdin.telegram.entities.MessageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemsCalculatingServiceImplTest {

    private MessageData messageData;

    @BeforeEach
    void setUp() {
        messageData = new MessageData();
        messageData.setMessageToBot(
                "last purchase:\n" +
                "190.48 pears\n" +
                "79.99 milk\n" +
                "105 bananas\n" +
                "130 cookies\n" +
                "half of sunflower oil 57.475\n" +
                "366 coffee\n" +
                "230,55 chicken's meat\n"
        );
    }

    @Test
    void analyzeDataDebug() {
//        DebtCalculatingService.getSumOfItems();
    }
}