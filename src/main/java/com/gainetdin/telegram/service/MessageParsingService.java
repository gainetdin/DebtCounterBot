package com.gainetdin.telegram.service;

import com.gainetdin.telegram.entity.ItemsData;
import com.gainetdin.telegram.entity.MessageData;

public interface MessageParsingService {
    ItemsData parseMessageToItems(MessageData messageData);
}
