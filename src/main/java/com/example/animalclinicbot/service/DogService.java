package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.DogException;
import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс сервис собаки
 */
@Service
public class DogService {
    private final DogRepository repository;

    public DogService(DogRepository repository) {
        this.repository = repository;
    }

    public Dog getById(Long id) {
        return repository.findById(id).orElseThrow(DogException::new);
    }

    public Dog addDog(Dog dog) {
        return repository.save(dog);
    }

    public Dog update(Dog dog) {
        if (dog.getId() != null) {
            if (getById(dog.getId()) != null) {
                return repository.save(dog);
            }
        }
        throw new DogException();
    }

    public void removeById(Long id) {
        repository.deleteById(id);
    }
}