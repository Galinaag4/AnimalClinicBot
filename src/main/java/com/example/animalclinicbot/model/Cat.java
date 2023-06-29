package com.example.animalclinicbot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * Класс содержит данные о питомце
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cat {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String breed;
    private int yearOfBirth;
    private String description;


    @Override
    public String toString() {
        return "Кличка кота - " + getName() + ", год рождения - " + getYearOfBirth() +
                ", порода - " + getBreed() + ", дополнительная информация о коте - " + description;
    }

}
