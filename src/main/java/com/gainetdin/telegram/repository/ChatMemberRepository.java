package com.gainetdin.telegram.repository;

import com.gainetdin.telegram.entity.ChatMemberEntity;
import org.springframework.data.repository.CrudRepository;

public interface ChatMemberRepository extends CrudRepository<ChatMemberEntity, Long> {
}
