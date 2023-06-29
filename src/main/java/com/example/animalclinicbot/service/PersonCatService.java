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

    private final PersonCatRepository personCatRepository;
    private static final Logger logger = LoggerFactory.getLogger(PersonCatService.class);

    public PersonCatService(PersonCatRepository personCatRepository) {
        this.personCatRepository = personCatRepository;
    }

    /**
     * метод получения владельца кота по id
     *
     * @param id
     * @return {@link PersonCatRepository#findById(Object)}
     * @throws PersonCatException
     * @see PersonCatService
     */
    public PersonCat getByIdPersonCat (Long id) {
        logger.info("Был вызван метод получения владельца кота по id={}", id);

        return this.personCatRepository.findById(id)
                .orElseThrow(PersonCatException::new);
    }


    /**
     * метод создания владельца кота
     *
     * @param personCat
     * @return {@link PersonCatRepository#save(Object)}
     * @see PersonCatService
     */
    public PersonCat createPersonCat(PersonCat personCat) {
        logger.info("Метод создания владельца кота");

        return this.personCatRepository.save(personCat);
    }
    /**
     * метод обновления данных владельца кота
     *
     * @param personCat
     * @return {@link PersonCatRepository#save(Object)}
     * @throws PersonCatException
     * @see PersonCatService
     */
    public PersonCat update(PersonCat personCat) {
        logger.info("Метод обновления данных владельца собаки");

        if (personCat.getId() != null) {
            if (getByIdPersonCat(personCat.getId()) != null) {
                return this.personCatRepository.save(personCat);
            }
        }
        throw new PersonCatException();
    }

    /**
     * метод удаления данных владельца кота по id
     *
     * @param id
     */
    public void deleteByIdPersonCat(Long id) {
        logger.info("Метод удаления данных владельца кота по id={}", id);

        this.personCatRepository.deleteById(id);
    }


    /**
     * Метод получения всех владельцев котов
     *
     * @return {@link PersonCatRepository#findAll()}
     * @see PersonCatService
     */
    public Collection<PersonCat> getAllPersonCat() {
        logger.info("Метод получения всех владельцев котов");

        return this.personCatRepository.findAll();
    }

    /**
     * Метод получения всех владельцев котов по id
     *
     * @param chatIdPersonCat
     * @return {@link PersonCatRepository#findByChatIdPersonCat(Long)}
     * @see PersonCatService
     */
    public Collection<PersonCat> getByChatIdPersonCat (Long chatIdPersonCat) {
        logger.info("Метод получения всех владельцев котов по id={}", chatIdPersonCat);

        return this.personCatRepository.findByChatIdPersonCat(chatIdPersonCat);
    }


}
