package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.DogException;
import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DogService {
    private final DogRepository dogRepository;
    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Dog getById(Long id) {
        logger.info("Was invoked method to get a dog by id={}", id);
        return this.dogRepository.findById(id)
                .orElseThrow(DogException::new);
    }

    public Dog create(Dog dog) {
        logger.info("Was invoked method to create a dog");
        return this.dogRepository.save(dog);
    }

    public Dog update(Dog dog) {
        logger.info("Was invoked method to update a dog");
        if (dog.getId() != null) {
            if (getById(dog.getId()) != null) {
                return this.dogRepository.save(dog);
            }
        }
        throw new DogException();
    }

    public Collection<Dog> getAll() {
        logger.info("Was invoked method to get all dogs");

        return this.dogRepository.findAll();
    }
    public void removeById(Long id) {
        logger.info("Was invoked method to remove a dog by id={}", id);

        this.dogRepository.deleteById(id);
    }
}
