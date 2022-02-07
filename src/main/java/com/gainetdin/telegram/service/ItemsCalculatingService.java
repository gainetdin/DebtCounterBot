package com.gainetdin.telegram.service;

import com.gainetdin.telegram.entity.ItemsData;

import java.math.BigDecimal;

public interface ItemsCalculatingService {
    BigDecimal getSumOfItems(ItemsData itemsData);
}
