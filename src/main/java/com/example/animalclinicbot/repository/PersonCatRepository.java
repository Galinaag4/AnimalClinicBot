package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.PersonCat;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Set;

public interface PersonCatRepository extends JpaRepository <PersonCat, Long> {

    Set<PersonCat> findByChatId(Long chatId);
}
