package com.gainetdin.telegram.repository;

import com.gainetdin.telegram.entity.ChatEntity;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatEntity, Long> {
}
