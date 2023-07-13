package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.PersonDogException;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.repository.PersonDogRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

/**
 * Класс сервис владельца собаки
 */
@Service
public class PersonDogService {
    private final PersonDogRepository repository;

    public PersonDogService(PersonDogRepository repository) {
        this.repository = repository;
    }

    public PersonDog save(PersonDog personDog) {
        repository.save(personDog);
        return personDog;
    }

    public PersonDog getById(Long id) {
        return repository.findById(id).orElseThrow(PersonDogException::new);
    }
    public Collection<PersonDog> getAll() {
        return repository.findAll();
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Set<PersonDog> getByChatId(Long chatId) {
        return repository.getPersonDogByChatId(chatId);
    }
}
