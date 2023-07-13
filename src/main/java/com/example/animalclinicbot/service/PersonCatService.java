package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.PersonCatException;
import com.example.animalclinicbot.exceptions.PersonDogException;
import com.example.animalclinicbot.model.PersonCat;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.repository.PersonCatRepository;
import com.example.animalclinicbot.repository.PersonDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс сервис владельца кота
 */
@Service
public class PersonCatService {

    private final PersonCatRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(PersonCatRepository.class);
    public PersonCatService(PersonCatRepository repository) {
        this.repository = repository;
    }
    public PersonCat getById(Long id) {
        logger.info("Was invoked method to get a CatOwners by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(PersonCatException::new);
    }
    public PersonCat create(PersonCat personCat) {
        logger.info("Was invoked method to create a catOwners");
        return this.repository.save(personCat);
    }
    public PersonCat update(PersonCat personCat ) {
        logger.info("Was invoked method to update a catOwners");
        if (personCat.getId() != null && getById(personCat.getId()) != null) {
            return repository.save(personCat);
        }
        throw new PersonCatException();
    }
    public void removeById(Long id) {
        logger.info("Was invoked method to remove a catOwners by id={}", id);
        this.repository.deleteById(id);
    }
    public Collection<PersonCat> getAll() {
        logger.info("Was invoked method to get all catOwners");

        return this.repository.findAll();
    }
    public Collection<PersonCat> getByChatId(Long chatId) {
        logger.info("Was invoked method to remove a catOwners by chatId={}", chatId);

        return this.repository.findByChatId(chatId);
    }
}
