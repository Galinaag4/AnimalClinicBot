package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.service.PersonDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
/**
 * Класс контроллер по владельцу собаки
 */
@RestController
@RequestMapping("person-dog")
public class PersonDogController {

    private final PersonDogService service;

    public PersonDogController(PersonDogService service) {
        this.service = service;
    }

    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{id}")
    public PersonDog getById(@PathVariable Long id) {
        return this.service.getById(id);
    }

    @Operation(summary = "Просмотр всех пользователей",
            description = "Просмотр всех пользователей, либо определенного пользователя по chat_id")
    @GetMapping("/all")
    public Collection<PersonDog> getAll(@RequestParam(required = false) Long chatId) {
        if (chatId != null) {
            return service.getByChatId(chatId);
        }
        return service.getAll();
    }
    @Operation(summary = "Создание пользователя")
    @PostMapping()
    public PersonDog save(@RequestBody PersonDog personDog) {
        return this.service.save(personDog);
    }
    @Operation(summary = "Изменение данных пользователя")
    @PutMapping
    public PersonDog update(@RequestBody PersonDog personDog) {
        return this.service.save(personDog);
    }
    @Operation(summary = "Удаление пользователей по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
