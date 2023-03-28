package com.example.animalclinicbot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameDog;
    private String breed;
    private int yearOfBirth;
    private String description;

    public Dog(String nameDog, String breed, int yearOfBirth, String description) {
        this.nameDog = nameDog;
        this.breed = breed;
        this.yearOfBirth = yearOfBirth;
        this.description = description;
    }

    public Dog() {

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
