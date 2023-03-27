package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository <Dog,Long> {
}
