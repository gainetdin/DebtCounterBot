package com.gainetdin.telegram.services;

import com.gainetdin.telegram.entities.ItemsData;

import java.math.BigDecimal;

public interface ItemsCalculatingService {
    BigDecimal getSumOfItems(ItemsData itemsData);
}
