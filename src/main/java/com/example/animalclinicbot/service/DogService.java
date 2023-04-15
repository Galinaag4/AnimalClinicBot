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
    private final DogRepository dogRepository;
    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }



    /**
     * метод получения собаки по id
     *
     * @param id
     * @return {@link DogRepository#findById(Object)}
     * @throws DogException
     * @see DogService
     */
    public Dog getById(Long id) {
        logger.info("Был вызван метод получения собаки по id={}", id);
        return this.dogRepository.findById(id)
                .orElseThrow(DogException::new);
    }

    /**
     * метод создания собаки
     *
     * @param dog
     * @return {@link DogRepository#save(Object)}
     * @see DogService
     */
    public Dog create(Dog dog) {
        logger.info("Был вызван метод создания собаки");
        return this.dogRepository.save(dog);
    }

    /**
     * метод обновления данных собаки
     *
     * @param dog
     * @return {@link DogRepository#save(Object)}
     * @throws DogException
     * @see DogService
     */
    public Dog update(Dog dog) {
        logger.info("Был вызван метод обновления данных собаки");
        if (dog.getId() != null) {
            if (getById(dog.getId()) != null) {
                return this.dogRepository.save(dog);
            }
        }
        throw new DogException();
    }

    /**
     * метод получения всех собак
     *
     * @return {@link DogRepository#findAll()}
     * @see DogService
     */
    public Collection<Dog> getAll() {
        logger.info("Был вызван метод получения всех собак");

        return this.dogRepository.findAll();
    }

    /**
     * метод удаления собаки по id
     *
     * @param id
     */
    public void removeById(Long id) {
        logger.info("Был вызван метод удаления собаки по id={}", id);

        this.dogRepository.deleteById(id);
    }
//    /**
//     * метод получения собак по id владельца
//     *
//     * @param id
//     */
//    public Collection<Dog> findDogsByPersonDogId(Long id) {
//        logger.info("Был вызван метод получения собак по id владельца {}", id);
//        Collection<Dog> dogs = this.dogRepository.findDogsByPersonDog_Id(id);
//        return dogs;
//    }
}
