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
    private final CatRepository repository;

    public CatService(CatRepository repository) {
        this.repository = repository;
    }

    /**
     Добавление нового кота в список
     */
    public Cat addCat(Cat cat) {
        return this.repository.save(cat);
    }

    /**
     получение кота по ID
     @param id кота
     */
    public Cat getById(Long id) {
        return this.repository.findById(id).orElseThrow(CatException::new);
    }
    /**
     * Обновление кота
     */

    public Cat update(Cat cat) {
        if (cat.getId() != null && getById(cat.getId()) != null) {
            return repository.save(cat);
        }
        throw new CatException();
    }
    /**
     * Удаление кота из списка по ID
     * @param id кота
     */

    public void removeById(Long id) {
        this.repository.deleteById(id);
    }


}
