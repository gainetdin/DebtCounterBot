package com.gainetdin.telegram.services;

import com.gainetdin.telegram.entities.ItemsData;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ItemsCalculatingServiceImpl implements ItemsCalculatingService {

    @Override
    public BigDecimal getSumOfItems(ItemsData itemsData) {
        Map<String, BigDecimal> items = itemsData.getItems();
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<String, BigDecimal> item : items.entrySet()) {
            sum = sum.add(item.getValue());
        }
        return sum;
    }
}
