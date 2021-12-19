package com.gainetdin.telegram.services;

import com.gainetdin.telegram.data.MessageData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebtCalculatingService {

    public static BigDecimal analyzeData(MessageData messageData) {
        String message = messageData.getMessageToBot();

        String[] lines = getLinesFromMessage(message);
        Map<String, BigDecimal> items = getItemsFromLines(lines);

        return getSumOfItems(items);
    }

    private static BigDecimal getSumOfItems(Map<String, BigDecimal> items) {
        BigDecimal sum = new BigDecimal(0);
        for (Map.Entry<String, BigDecimal> item : items.entrySet()) {
            sum = sum.add(item.getValue());
        }
        return sum;
    }

    private static Map<String, BigDecimal> getItemsFromLines(String[] lines) {
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

    private static String[] getLinesFromMessage(String message) {
        String lineDelimiter = "[\\n\\r]+";
        return message.split(lineDelimiter);
    }

}
