package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.PersonCat;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.service.PersonCatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Класс контроллер по владельцу кота
 */
@RestController
@RequestMapping("person-cat")
public class PersonCatController {
    private final PersonCatService service;

    public PersonCatController(PersonCatService service) {
        this.service = service;
    }

    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{id}")
    public PersonCat getById(@PathVariable Long id) {
        return this.service.getById(id);
    }
    @Operation(summary = "Создание пользователя")
    @PostMapping()
    public PersonCat save(@RequestBody PersonCat personCat) {
        return this.service.create(personCat);
    }
    @Operation(summary = "Изменение данных пользователя")
    @PutMapping
    public PersonCat update(@RequestBody PersonCat personCat) {
        return this.service.update(personCat);
    }
    @Operation(summary = "Удаление пользователей по id")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        this.service.removeById(id);
    }
    @Operation(summary = "Просмотр всех пользователей",
            description = "Просмотр всех пользователей, либо определенного пользователя по chat_id")
    @GetMapping("/all")
    public Collection<PersonCat> getAll(@RequestParam(required = false) Long chatId) {
        if (chatId != null) {
            return this.service.getByChatId(chatId);
        }
        return this.service.getAll();
    }
}
