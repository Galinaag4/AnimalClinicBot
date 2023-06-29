package com.example.animalclinicbot.model;

import com.example.animalclinicbot.constant.TypeOfShelter;
import liquibase.pro.packaged.P;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class UserContext {
    @Id
    private Long chatId;
    @Enumerated
    private TypeOfShelter typeOfShelter;
    @OneToOne
    private PersonCat personCat;
    @OneToOne
    private PersonDog personDog;

    public UserContext(Long chatId, TypeOfShelter typeOfShelter) {
        this.chatId = chatId;
        this.typeOfShelter = typeOfShelter;
    }

}