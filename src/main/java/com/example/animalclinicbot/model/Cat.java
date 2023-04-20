package com.example.animalclinicbot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс содержит данные о питомце
 */
@Entity
@Table (name = "Cat")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * имя животного
     */
    @Column(name = "name_cat", nullable = false)
    private String nameCat;

    /**
     * порода животного
     */
    @Column (name = "breed_cat", nullable = false)
    private String breedCat;

    /**
     * год рождения животного
     */
    @Column (name = "year_of_birth_cat", nullable = false)
    private int yearOfBirthCat;

    /**
     * описание животного
     */
    @Column (name = "description_cat", nullable = false)
    private String descriptionCat;

    @ManyToOne
    @JoinColumn (name = "personCat_id")
    @JsonBackReference
    private PersonCat personCat;

    public Cat() {

    }

    public Cat(String nameCat, String breedCat, int yearOfBirthCat, String descriptionCat, PersonCat personCat) {
        this.nameCat = nameCat;
        this.breedCat = breedCat;
        this.yearOfBirthCat = yearOfBirthCat;
        this.descriptionCat = descriptionCat;
        this.personCat = personCat;
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

    public String getBreedCat() {
        return breedCat;
    }

    public void setBreedCat(String breedCat) {
        this.breedCat = breedCat;
    }

    public int getYearOfBirthCat() {
        return yearOfBirthCat;
    }

    public void setYearOfBirthCat(int yearOfBirthCat) {
        this.yearOfBirthCat = yearOfBirthCat;
    }

    public String getDescriptionCat() {
        return descriptionCat;
    }

    public void setDescriptionCat(String descriptionCat) {
        this.descriptionCat = descriptionCat;
    }

    public PersonCat getPersonCat() {
        return personCat;
    }

    public void setPersonCat(PersonCat personCat) {
        this.personCat = personCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return yearOfBirthCat == cat.yearOfBirthCat && id.equals(cat.id) &&
                nameCat.equals(cat.nameCat) && breedCat.equals(cat.breedCat) &&
                descriptionCat.equals(cat.descriptionCat) && personCat.equals(cat.personCat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameCat, breedCat, yearOfBirthCat, descriptionCat, personCat);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", nameCat='" + nameCat + '\'' +
                ", breedCat='" + breedCat + '\'' +
                ", yearOfBirthCat=" + yearOfBirthCat +
                ", descriptionCat='" + descriptionCat + '\'' +
                ", personCat=" + personCat +
                '}';
    }
}
