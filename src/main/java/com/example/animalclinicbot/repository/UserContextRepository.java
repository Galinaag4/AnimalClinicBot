package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.UserContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserContextRepository extends CrudRepository<UserContext,Long> {
    Optional<UserContext> findByChatId(Long chatId);
}
