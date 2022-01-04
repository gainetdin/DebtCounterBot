package com.gainetdin.telegram.services;

import com.gainetdin.telegram.entities.ItemsData;
import com.gainetdin.telegram.entities.MessageData;

public interface MessageParsingService {
    ItemsData parseMessageToItems(MessageData messageData);
}
