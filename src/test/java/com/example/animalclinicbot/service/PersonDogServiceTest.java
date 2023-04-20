package com.example.animalclinicbot.service;


import com.example.animalclinicbot.model.PersonDog;

import com.example.animalclinicbot.repository.PersonDogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PersonDogServiceTest {
    @InjectMocks
    private PersonDogService personDogService;
    @Mock
    private PersonDogRepository personDogRepository;


    @Test
    void addNewPersonDog() {
        PersonDog expected = new PersonDog("Andrew");
        when(personDogRepository.save(any())).thenReturn(expected);
        PersonDog actual = personDogService.create(expected);
        assertThat(actual).isEqualTo(expected);
        verify(personDogRepository, Mockito.only()).save(expected);
    }

    @Test
    void getAllPersonDog() {
        Collection<PersonDog> expected = List.of(new PersonDog("Maxim"));
        when(personDogRepository.findAll()).thenReturn((List<PersonDog>) expected);
        Collection<PersonDog> actual = personDogService.getAll();
        assertThat(actual).isEqualTo(expected);
        verify(personDogRepository, Mockito.only()).findAll();
    }

    @Test
    void testGetById() {
        Long id = 1L;
        PersonDog personDog = new PersonDog(id, "Maxim");
        when(personDogRepository.findById(id)).thenReturn(Optional.of(personDog));
        PersonDog result = personDogService.getById(id);
        assertEquals(personDog, result);
        verify(personDogRepository, times(1)).findById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        PersonDog personDog = new PersonDog(id, "George");
        when(personDogRepository.findById(id)).thenReturn(Optional.of(personDog));
        when(personDogRepository.save(personDog)).thenReturn(personDog);
        PersonDog result = personDogService.update(personDog);
        assertEquals(personDog, result);
        verify(personDogRepository, times(1)).findById(id);
        verify(personDogRepository, times(1)).save(personDog);
    }

    @Test
    void testRemoveById() {
        Long id = 1L;
        doNothing().when(personDogRepository).deleteById(id);
        personDogService.removeById(id);
        verify(personDogRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetByChatId() {
        Long id = 1L;
        Collection<PersonDog> expected = Set.of(new PersonDog(id, "Maxim"));
        when(personDogRepository.findByChatId(id)).thenReturn((Set<PersonDog>) expected);
        Collection<PersonDog> result = personDogService.getByChatId(id);
        assertEquals(expected, result);
        verify(personDogRepository, times(1)).findByChatId(id);
    }


}
