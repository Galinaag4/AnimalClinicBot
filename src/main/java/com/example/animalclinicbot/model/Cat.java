package com.example.animalclinicbot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

//@Entity
public class Cat {


    @Id
    @GeneratedValue


    private Long id;

    private String nameCat;
    private String breed;
    private int yearOfBirth;
    private String description;

    public Cat(String nameCat, String breed, int yearOfBirth, String description) {
        this.nameCat = nameCat;
        this.breed = breed;
        this.yearOfBirth = yearOfBirth;
        this.description = description;
    }

    public Cat() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
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
        if (!(o instanceof Cat cat)) return false;
        return getYearOfBirth() == cat.getYearOfBirth() && Objects.equals(getId(), cat.getId()) && Objects.equals(getNameCat(), cat.getNameCat()) && Objects.equals(getBreed(), cat.getBreed()) && Objects.equals(getDescription(), cat.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameCat(), getBreed(), getYearOfBirth(), getDescription());
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", nameCat='" + nameCat + '\'' +
                ", breed='" + breed + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", description='" + description + '\'' +
                '}';
    }
}
