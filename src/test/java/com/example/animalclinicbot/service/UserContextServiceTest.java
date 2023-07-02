package com.example.animalclinicbot.service;

import com.example.animalclinicbot.constant.TypeOfShelter;
import com.example.animalclinicbot.model.UserContext;
import com.example.animalclinicbot.repository.UserContextRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Service Test class for user state tracking (Класс Тестов сервисов для отслеживания состояния пользователя)
 *
 * @author Молодцова Анна
 **/

    @ExtendWith(MockitoExtension.class)
    public class UserContextServiceTest {
        @Mock
        private UserContextRepository userContextRepository;
        @InjectMocks
        private UserContextService userContextService;

        @Test
        public void testSaveContext() {
            Long testId = 1L;
            UserContext userContext = new UserContext(testId, TypeOfShelter.DOG);
            when(userContextRepository.save(userContext)).thenReturn(userContext);
            UserContext saveContext = userContextService.saveUserContext(userContext);
            Mockito.verify(userContextRepository, Mockito.times(1)).save(userContext);
            Assertions.assertEquals(userContext.getChatId(), saveContext.getChatId());
            Assertions.assertEquals(userContext.getTypeOfShelter(), saveContext.getTypeOfShelter());
            Assertions.assertEquals(userContext.getPersonDog(), saveContext.getPersonCat());
            Assertions.assertEquals(userContext.getPersonCat(), saveContext.getPersonCat());
        }

        @Test
        public void testGetByChatId() {
            Optional<UserContext> userContext = Optional.of(new UserContext(1L, TypeOfShelter.DOG));
            when(userContextRepository.findByChatId(anyLong())).thenReturn(userContext);
            userContextRepository.findByChatId(userContext.get().getChatId());
            assertNotNull(userContext);
            Assertions.assertEquals(userContext.get(), userContext.get());
        }
        @Test
        public void testGetAll() {
            List<UserContext> userContextList = new ArrayList<>();
            UserContext context22 = new UserContext(1L, TypeOfShelter.DOG);
            userContextList.add(context22);
            UserContext context23 = new UserContext(2L, TypeOfShelter.CAT);
            userContextList.add(context23);
            Mockito.when(userContextRepository.findAll()).thenReturn(userContextList);
            Collection<UserContext> result = userContextService.getAll();
            Assertions.assertEquals(userContextList, result);
            Mockito.verify(userContextRepository, Mockito.times(1)).findAll();
        }
    }
