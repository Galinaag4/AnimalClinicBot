package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.service.CatService;
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
 * Класс контроллер по коту
 */

@RestController
@RequestMapping("cat")
public class CatController {
    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @Operation (summary = "Получение кота по id",
                    responses =
                            {@ApiResponse(responseCode = "200",
                                    description = "Кот, найденный по id",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Cat.class)))
                            },
                    tags = "Cat"
            )
    @GetMapping("/{id}")
    public Cat getCatById(@Parameter (description = "cat id") @PathVariable Long id) {
        return this.catService.getByIdCat(id);
    }


    @Operation (summary = "Создание кота",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Созданный кот",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            tags = "Cat"
    )
    @PostMapping()
    public Cat saveCat (@RequestBody Cat cat) {
        return this.catService.createCat(cat);
    }


    @Operation(summary = "Редактирование данных кота",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Кот с измененными данными",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            tags = "Cat"
    )
    @PutMapping()
    public Cat updateCat (@RequestBody Cat cat) {
        return this.catService.updateCat(cat);
    }


    @Operation(summary = "Удаление кота по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный кот",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            },
            tags = "Cat"
    )
    @DeleteMapping("/{id}")
    public void deleteCat (@Parameter(description = "cat id") @PathVariable Long id) {
        this.catService.deleteCatById(id);
    }


    @Operation(summary = "Просмотр всех котов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все коты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            },
            tags = "Cat"
    )
    @GetMapping("/all")
    public Collection <Cat> getAllCats() {
        return this.catService.getAll();
    }

    @Operation(summary = "Просмотр всех котов по id владельца",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все коты, полученные по id владельца",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            },
            tags = "Cat"
    )
    @GetMapping("/cat/{id}")
    public ResponseEntity<Collection<Cat>> findCatsByPersonCatId(@PathVariable Long id) {
        return ResponseEntity.ok(this.catService.findCatsByIdPersonCat(id));
    }

}
