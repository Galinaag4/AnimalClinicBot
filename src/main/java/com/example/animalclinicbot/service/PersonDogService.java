package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.PersonDogException;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.repository.PersonDogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс сервис владельца собаки
 */
@Service
public class PersonDogService {

    private final PersonDogRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(PersonDogService.class);

    public PersonDogService(PersonDogRepository repository) {
        this.repository = repository;
    }

    /**
     * метод получения владельца собаки по id
     *
     * @param id
     * @return {@link PersonDogRepository#findById(Object)}
     * @throws PersonDogException
     * @see PersonDogService
     */
    public PersonDog getById(Long id) {
        logger.info("Был вызван метод получения владельца собаки по id={}", id);

        return this.repository.findById(id)
                .orElseThrow(PersonDogException::new);
    }

    /**
     * метод создания владельца собаки
     *
     * @param personDog
     * @return {@link PersonDogRepository#save(Object)}
     * @see PersonDogService
     */
    public PersonDog create(PersonDog personDog) {
        logger.info("Метод создания владельца собаки");

        return this.repository.save(personDog);
    }

    /**
     * метод обновления данных владельца собаки
     *
     * @param personDog
     * @return {@link PersonDogRepository#save(Object)}
     * @throws PersonDogException
     * @see PersonDogService
     */
    public PersonDog update(PersonDog personDog) {
        logger.info("Метод обновления данных владельца собаки");

        if (personDog.getId() != null) {
            if (getById(personDog.getId()) != null) {
                return this.repository.save(personDog);
            }
        }
        throw new PersonDogException();
    }

    /**
     * метод удаления данных владельца собаки по id
     *
     * @param id
     */
    public void removeById(Long id) {
        logger.info("Метод удаления данных владельца собаки по id={}", id);

        this.repository.deleteById(id);
    }

    /**
     * Метод получения всех владельцев собак
     *
     * @return {@link PersonDogRepository#findAll()}
     * @see PersonDogService
     */
    public Collection<PersonDog> getAll() {
        logger.info("Метод получения всех владельцев собак");

        return this.repository.findAll();
    }

    /**
     * Метод получения всех владельцев собак по id
     *
     * @param chatId
     * @return {@link PersonDogRepository#findByChatId(Long)}
     * @see PersonDogService
     */
    public Collection<PersonDog> getByChatId(Long chatId) {
        logger.info("Метод получения всех владельцев собак по id={}", chatId);

        return this.repository.findByChatId(chatId);
    }
}
