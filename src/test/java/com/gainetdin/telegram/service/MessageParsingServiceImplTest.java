package com.gainetdin.telegram.service;

import com.gainetdin.telegram.entity.ItemsData;
import com.gainetdin.telegram.entity.MessageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class MessageParsingServiceImplTest {

    private MessageParsingService messageParsingService;
    private MessageData messageData;

    @BeforeEach
    void setUp() {
        messageData = new MessageData();
        messageParsingService = new MessageParsingServiceImpl(new ItemsData());
        messageData.setMessageToBot(
                "last purchase:\n" +
                "190.48 pears\n" +
                "105 bananas\n" +
                "half of sunflower oil 57.475\n" +
                "230,55 chicken's meat\n"
        );
    }

    @Test
    void messageDataCanBeParsedToDifferentItems() {
        Map<String, BigDecimal> expectedMap = new HashMap<>();
        expectedMap.put("pears", new BigDecimal("190.48"));
        expectedMap.put("bananas", new BigDecimal("105.00"));
        expectedMap.put("half of sunflower oil", new BigDecimal("57.48"));
        expectedMap.put("chicken's meat", new BigDecimal("230.55"));

        Map<String, BigDecimal> actualMap = messageParsingService.parseMessageToItems(messageData).getItems();
        Assertions.assertEquals(expectedMap, actualMap);
    }
}