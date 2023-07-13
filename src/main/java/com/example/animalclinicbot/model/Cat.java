package com.example.animalclinicbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Класс модель котов
 * @author Герасименко Максим
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cat")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
