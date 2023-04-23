package com.example.animalclinicbot.service;

import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.repository.CatRepository;
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
public class CatServiceTest {

    @InjectMocks
    private CatService catService;

    @Mock
    private CatRepository catRepository;

    @Test
    void addNewCat() {
        Cat expected = new Cat("Murzik");
        when(catRepository.save(any())).thenReturn(expected);
        Cat actual = catService.createCat(expected);
        assertThat(actual).isEqualTo(expected);
        verify(catRepository, Mockito.only()).save(expected);
    }

    @Test
    void getAllCat() {
        Collection<Cat> expected = List.of(new Cat("Mars"));
        when(catRepository.findAll()).thenReturn((List<Cat>) expected);
        Collection<Cat> actual = catService.getAll();
        assertThat(actual).isEqualTo(expected);
        verify(catRepository, Mockito.only()).findAll();
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Cat cat = new Cat(id, "Matroskin");
        when(catRepository.findById(id)).thenReturn(Optional.of(cat));
        Cat result = catService.getByIdCat(id);
        assertEquals(cat, result);
        verify(catRepository, times(1)).findById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Cat cat = new Cat(id, "Cat");

        when(catRepository.findById(id)).thenReturn(Optional.of(cat));
        when(catRepository.save(cat)).thenReturn(cat);
        Cat result = catService.updateCat(cat);
        assertEquals(cat, result);
        verify(catRepository, times(1)).findById(id);
        verify(catRepository, times(1)).save(cat);
    }

    @Test
    void testRemoveById() {
        Long id = 1L;
        doNothing().when(catRepository).deleteById(id);
        catService.deleteCatById(id);
        verify(catRepository, times(1)).deleteById(id);
    }


}
