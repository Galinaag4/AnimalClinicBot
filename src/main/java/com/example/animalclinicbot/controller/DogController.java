package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Класс контроллер по собаке
 */

@RestController
@RequestMapping("dog")
public class DogController {

    private final DogService dogService;

    public DogController(DogService dogService)
    {
        this.dogService = dogService;
    }

    @Operation(summary = "Получение собаки по id")
    @GetMapping("/{id}")
    public Dog getDogById(@PathVariable Long id) {
        return dogService.getById(id);
    }

    @Operation(summary = "Создание собаки")
    @PostMapping()
    public Dog addDog(@RequestBody Dog dog) {
        return dogService.addDog(dog);
    }

    @Operation(summary = "Обновление данных собаки")
    @PutMapping("/{id}")
    public Dog updateDogById(@PathVariable Long id, @RequestBody Dog dog) {
        return dogService.update(dog);
    }

    @Operation(summary = "Удаление собаки по id")
    @DeleteMapping("/{id}")
    public void removeDog(@PathVariable Long id) {
        dogService.removeById(id);
    }
}