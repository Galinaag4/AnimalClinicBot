package com.example.animalclinicbot.service;

import com.example.animalclinicbot.model.UserContext;
import com.example.animalclinicbot.repository.UserContextRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
@Service
public class UserContextService {
    private final UserContextRepository userContextRepository;

    public UserContextService(UserContextRepository userContextRepository) {
        this.userContextRepository = userContextRepository;
    }
    public UserContext saveUserContext(UserContext userContext) {
        return userContextRepository.save(userContext);
    }
    public Collection<UserContext> getAll() {
      return userContextRepository.findAll();
    }

    public Optional<UserContext> getByChatId(Long chatId) {

        return userContextRepository.findByChatId(chatId);
    }

}
