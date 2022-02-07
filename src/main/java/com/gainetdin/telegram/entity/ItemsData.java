package com.gainetdin.telegram.entity;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ItemsData {

    Map<String, BigDecimal> items;

    public Map<String, BigDecimal> getItems() {
        return items;
    }

    public void setItems(Map<String, BigDecimal> items) {
        this.items = items;
    }
}
