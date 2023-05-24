package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.UserContext;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserContextRepository extends JpaRepository<UserContext,Long> {
    UserContext findUserByChatId(Long chatId);
}
