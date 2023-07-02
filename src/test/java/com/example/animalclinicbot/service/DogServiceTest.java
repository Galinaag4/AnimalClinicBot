package com.example.animalclinicbot.service;


import com.example.animalclinicbot.exceptions.DogException;
import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.repository.DogRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DogServiceTest {
    @InjectMocks
    private DogService dogService;
    @Mock
    private DogRepository dogRepository;



    @Test
    void addNewDog() {
        Dog expected = new Dog("Tuzik");
        when(dogRepository.save(any())).thenReturn(expected);
        Dog actual = dogService.create(expected);
        assertThat(actual).isEqualTo(expected);
        verify(dogRepository, Mockito.only()).save(expected);
    }

    @Test
    void getAllDog() {
        Collection<Dog> expected = List.of(new Dog("Bobik"));
        when(dogRepository.findAll()).thenReturn((List<Dog>) expected);
        Collection<Dog> actual = dogService.getAll();
        assertThat(actual).isEqualTo(expected);
        verify(dogRepository, Mockito.only()).findAll();
    }
    @Test
    void testGetById() {
        Long id = 1L;
        Dog dog = new Dog(id, "Tuzik");
        when(dogRepository.findById(id)).thenReturn(Optional.of(dog));
        Dog result = dogService.getById(id);
        assertEquals(dog, result);
        verify(dogRepository, times(1)).findById(id);
    }
    @Test
    void testUpdate() {
        Long id = 1L;
        Dog dog = new Dog(id, "Buddy");

        when(dogRepository.findById(id)).thenReturn(Optional.of(dog));
        when(dogRepository.save(dog)).thenReturn(dog);
        Dog result = dogService.update(dog);
        assertEquals(dog, result);
        verify(dogRepository, times(1)).findById(id);
        verify(dogRepository, times(1)).save(dog);
    }
    @Test
    void testRemoveById() {
        Long id = 1L;
        doNothing().when(dogRepository).deleteById(id);
        dogService.removeById(id);
        verify(dogRepository, times(1)).deleteById(id);
    }








}
