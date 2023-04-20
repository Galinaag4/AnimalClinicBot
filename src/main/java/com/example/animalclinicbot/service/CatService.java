package com.example.animalclinicbot.service;


import com.example.animalclinicbot.exceptions.CatException;
import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.repository.CatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс сервис кота
 */
@Service
public class CatService {

    private CatRepository catRepository;
    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * метод получения кота по id
     *
     * @param id
     * @return {@link CatRepository#findById(Object)}
     * @throws CatException
     * @see CatService
     */
    public Cat getByIdCat(Long id) {
        logger.info("Был вызван метод получения кота по id={}", id);

        return this.catRepository.findById(id)
                .orElseThrow(CatException::new);
    }

    /**
     * метод создания кота
     *
     * @param cat
     * @return {@link CatRepository#save(Object)}
     * @see CatService
     */
    public Cat createCat(Cat cat) {
        logger.info("Был вызван метод создания собаки");

        return this.catRepository.save(cat);
    }

    /**
     * метод редактирования кота
     *
     * @param cat
     * @return {@link CatRepository#save(Object)}
     * @throws CatException
     * @see CatService
     */
    public Cat updateCat(Cat cat) {
        logger.info("Был вызван метод редактирования данных кота");

        if (cat.getId() != null) {
            if (getByIdCat(cat.getId()) != null) {
                return this.catRepository.save(cat);
            }
        }
        throw new CatException();
    }

    /**
     * метод получения всех котов
     *
     * @return {@link CatRepository#findAll()}
     * @see CatService
     */
    public Collection<Cat> findAll() {
        logger.info("Был вызван метод получения всех котов");

        return this.catRepository.findAll();
    }

    /**
     * метод удаления кота по id
     *
     * @param id
     */
    public void deleteCatById(Long id) {
        logger.info("Был вызван метод удаления кота по id={}", id);

        this.catRepository.deleteById(id);
    }

    /**
     * метод получения собак по id владельца
     *
     * @param id
     */
    public Collection<Cat> findCatsByIdPersonCat (Long id) {
        logger.info("Был вызван метод получения всех котов по id владельца {}", id);

        Collection<Cat> cats = this.catRepository.findCatsByPersonCat_Id(id);
        return cats;
    }

}
