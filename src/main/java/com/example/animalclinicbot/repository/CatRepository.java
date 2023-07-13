package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CatRepository extends JpaRepository <Cat, Long> {

}
