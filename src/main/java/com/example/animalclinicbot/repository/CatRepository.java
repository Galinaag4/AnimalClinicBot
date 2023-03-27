package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository <Cat,Long> {
}
