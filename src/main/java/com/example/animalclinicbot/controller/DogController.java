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

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @Operation(summary = "Получение питомца по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Собака, найденная по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            },
            tags = "Dog"
    )
    @GetMapping("/{id}")
    public Dog getDogById(@Parameter(description = "dog id") @PathVariable Long id) {
        return this.dogService.getById(id);
    }

    @Operation(summary = "Создание собаки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Созданная собака",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            tags = "Dog"
    )
    @PostMapping()
    public Dog saveDog(@RequestBody Dog dog) {
        return this.dogService.create(dog);
    }

    @Operation(summary = "Изменение данных у собаки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Собака с измененными данными",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            tags = "Dog"
    )
    @PutMapping()
    public Dog updateDog(@RequestBody Dog dog) {
        return this.dogService.update(dog);
    }

    @Operation(summary = "Удаление собаки по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленная собака",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            },
            tags = "Dog"
    )
    @DeleteMapping("/{id}")
    public void removeDog(@Parameter(description = "dog id") @PathVariable Long id) {
        this.dogService.removeById(id);
    }

    @Operation(summary = "Просмотр всех собак",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все собаки",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            },
            tags = "Dog"
    )
    @GetMapping("/all")
    public Collection<Dog> getAllDogs() {
        return this.dogService.getAll();
    }
    @Operation(summary = "Просмотр всех собак по id владельца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все собаки, полученные по id владельца",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            },
            tags = "Dog"
    )
    @GetMapping("/byPersonDog/{id}")
    public ResponseEntity<Collection<Dog>> findDogsByPersonDogId(@PathVariable Long id) {
        return ResponseEntity.ok(this.dogService.findDogsByPersonDogId(id));
    }
}
