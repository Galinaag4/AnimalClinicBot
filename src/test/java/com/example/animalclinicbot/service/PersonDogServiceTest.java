package com.example.animalclinicbot.service;


import com.example.animalclinicbot.exceptions.PersonDogException;
import com.example.animalclinicbot.model.PersonDog;

import com.example.animalclinicbot.repository.PersonDogRepository;
import liquibase.pro.packaged.P;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * Класс Тестов сервисов для владельцев собак
 * @author Агуреева Галина
 **/

@ExtendWith(MockitoExtension.class)
class PersonDogServiceTest {
    @Mock
    private PersonDogRepository repository;
    @InjectMocks
    private PersonDogService service;
    @Test
    public void testGetById() {
        Long testId = 1L;
        PersonDog personDog = new PersonDog(1L, "Vasya", 321L);
        Mockito.when(repository.findById(testId)).thenReturn(Optional.of(personDog));
        PersonDog result = service.getById(testId);
        Assertions.assertEquals(personDog, result);
    }

    @Test
    public void testGetByChatId() {
        Long testChatId = 5456L;
        PersonDog personDog = new PersonDog(1L, "Vasya", testChatId);
        Set<PersonDog> personDogSet = new HashSet<>();
        personDogSet.add(personDog);
        Mockito.when(repository.findByChatId(testChatId)).thenReturn(personDogSet);
        Set<PersonDog> result = service.getByChatId(testChatId);
        Assertions.assertEquals(personDogSet, result);
    }

    @Test
    public void testGetByIdNotFound() throws PersonDogException {
        Long testId = 1L;
        Mockito.when(repository.findById(testId)).thenReturn(Optional.empty());
        Assertions.assertThrows(PersonDogException.class, () -> service.getById(testId));
    }

    @Test
    public void testUpdatePersonDog() {
        PersonDog personDog1 = new PersonDog();
        personDog1.setName("Vasya");
        personDog1.setId(1L);
        PersonDog personDog2 = new PersonDog();
        personDog2.setName("Volodya");
        personDog2.setId(2L);
        PersonDog personDog3 = new PersonDog();
        personDog3.setName("Bob");
        personDog3.setId(3L);
        PersonDog updatedPersonDog1 = service.save(personDog1);
        PersonDog updatedPersonDog2 = service.save(personDog2);
        PersonDog updatedPersonDog3 = service.save(personDog3);
        Assertions.assertEquals(updatedPersonDog1.getName(), "Vasya");
        Assertions.assertEquals(updatedPersonDog1.getId(), Long.valueOf(1L));
        Assertions.assertEquals(updatedPersonDog2.getName(), "Volodya");
        Assertions.assertEquals(updatedPersonDog2.getId(), Long.valueOf(2L));
        Assertions.assertEquals(updatedPersonDog3.getName(), "Bob");
        Assertions.assertEquals(updatedPersonDog3.getId(), Long.valueOf(3L));
    }
}