package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DogRepository extends JpaRepository <Dog,Long> {
 Collection<Dog> findDogsByPersonDog_Id(Long id);
}
