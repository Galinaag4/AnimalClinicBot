package com.example.animalclinicbot.service;

import com.example.animalclinicbot.model.PersonCat;
import com.example.animalclinicbot.repository.PersonCatRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PersonCatServiceTest {

    @InjectMocks
    private PersonCatService personCatService;

    @Mock
    private PersonCatRepository personCatRepository;

    @Test
    void getByIdPersonCat() {

        Long id = 1L;
        PersonCat personCat = new PersonCat(id, "Maxim");
        when(personCatRepository.findById(id)).thenReturn(Optional.of(personCat));
        PersonCat result = personCatService.getByIdPersonCat(id);
        assertEquals(personCat, result);
        verify(personCatRepository, times(1)).findById(id);

    }

    @Test
    void createPersonCat() {

            PersonCat expected = new PersonCat("Masha");
            when(personCatRepository.save(any())).thenReturn(expected);
            PersonCat actual = personCatService.createPersonCat(expected);
            assertThat(actual).isEqualTo(expected);
            verify(personCatRepository, Mockito.only()).save(expected);

    }

    @Test
    void deleteByIdPersonCat() {
        Long id = 1L;
        doNothing().when(personCatRepository).deleteById(id);
        personCatService.deleteByIdPersonCat(id);
        verify(personCatRepository, times(1)).deleteById(id);
    }

    @Test
    void getAllPersonCat() {
        Collection<PersonCat> expected = List.of(new PersonCat("Maxim"));
        when(personCatRepository.findAll()).thenReturn((List<PersonCat>) expected);
        Collection<PersonCat> actual = personCatService.getAllPersonCat();
        assertThat(actual).isEqualTo(expected);
        verify(personCatRepository, Mockito.only()).findAll();
    }

    @Test
    void getByChatIdPersonCat() {
        Long id = 1L;
        Collection<PersonCat> expected = Set.of(new PersonCat(id, "Maxim"));
        when(personCatRepository.findByChatIdPersonCat(id)).thenReturn((Set<PersonCat>) expected);
        Collection<PersonCat> result =personCatService.getByChatIdPersonCat(id);
        assertEquals(expected, result);
        verify(personCatRepository, times(1)).findByChatIdPersonCat(id);
    }
}