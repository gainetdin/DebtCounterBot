package com.gainetdin.telegram.service;

import com.gainetdin.telegram.entity.ItemsData;
import com.gainetdin.telegram.entity.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageParsingServiceImpl implements MessageParsingService {

    ItemsData itemsData;

    @Override
    public ItemsData parseMessageToItems(MessageData messageData) {
        String message = messageData.getMessageToBot();
        String[] lines = getLinesFromMessage(message);
        Map<String, BigDecimal> items = getItemsFromLines(lines);
        itemsData.setItems(items);
        return itemsData;
    }

    private String[] getLinesFromMessage(String message) {
        String lineDelimiter = "[\\n\\r]+";
        return message.split(lineDelimiter);
    }

    private Map<String, BigDecimal> getItemsFromLines(String[] lines) {
        Pattern anyDecimalNumber = Pattern.compile("-?\\d+([.,]\\d+)?");
        Map<String, BigDecimal> items = new HashMap<>();
        for (String line : lines) {
            Matcher numberMatcher = anyDecimalNumber.matcher(line);
            if(numberMatcher.find()) {
                String priceValue = numberMatcher.group().replace(",", ".");
                BigDecimal price = new BigDecimal(priceValue).setScale(2, RoundingMode.HALF_EVEN);
                String item = numberMatcher.replaceAll(" ").trim();

                items.put(item, price);
            }
        }
        return items;
    }

    @Autowired
    public MessageParsingServiceImpl(ItemsData itemsData) {
        this.itemsData = itemsData;
    }
}
