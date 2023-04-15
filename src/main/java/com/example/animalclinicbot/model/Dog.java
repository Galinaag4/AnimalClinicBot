package com.example.animalclinicbot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;
/**
 * Класс содержит данные о питомце
 */
@Entity
public class Dog {
    /**
     * идентификатор записи
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * имя животного
     */
    @Column (name = "name_dog", nullable = false)
    private String nameDog;
    /**
     * порода животного
     */
    @Column (name = "breed", nullable = false)
    private String breed;
    /**
     * год рождения животного
     */
    @Column (name = "year_of_birth", nullable = false)
    private int yearOfBirth;
    /**
     * описание животного
     */
    @Column (name = "description", nullable = false)
    private String description;


    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private PersonDog personDog;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;


    public Dog(Long id, String nameDog, String breed, int yearOfBirth, String description, PersonDog personDog, Report report) {
        this.nameDog = nameDog;
        this.breed = breed;
        this.yearOfBirth = yearOfBirth;
        this.description = description;
        this.personDog = personDog;
        this.report = report;
    }

    public Dog() {

    }

    public Dog(String nameDog) {
        this.nameDog = nameDog;
    }

    public Dog(Long id, String nameDog) {
        this.id = id;
        this.nameDog = nameDog;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDog() {
        return nameDog;
    }

    public void setNameDog(String nameDog) {
        this.nameDog = nameDog;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dog dog)) return false;
        return getYearOfBirth() == dog.getYearOfBirth() && Objects.equals(getId(), dog.getId()) && Objects.equals(getNameDog(), dog.getNameDog()) && Objects.equals(getBreed(), dog.getBreed()) && Objects.equals(getDescription(), dog.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameDog(), getBreed(), getYearOfBirth(), getDescription());
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", nameCat='" + nameDog + '\'' +
                ", breed='" + breed + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", description='" + description + '\'' +
                '}';
    }
}
