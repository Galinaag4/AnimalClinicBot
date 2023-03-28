package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.service.DogService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("dog")
public class DogController {
    private final DogService service;

    public DogController(DogService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public Dog getById(@Parameter(description = "dog id") @PathVariable Long id) {
        return this.service.getById(id);
    }
    @PostMapping()
    public Dog save(@RequestBody Dog dog) {
        return this.service.create(dog);
    }
    @PutMapping()
    public Dog update(@RequestBody Dog dog) {
        return this.service.update(dog);
    }
    @DeleteMapping("/{id}")
    public void remove(@Parameter (description = "dog id")@PathVariable Long id) {
        this.service.removeById(id);
    }
    @GetMapping("/all")
    public Collection<Dog> getAll() {
        return this.service.getAll();
    }

}
