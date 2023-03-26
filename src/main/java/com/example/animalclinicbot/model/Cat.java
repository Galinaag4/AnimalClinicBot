package com.example.animalclinicbot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

//@Entity
public class Cat {

//    @Id
    private Long id;

    private String nameCat;
    private Integer ageCat;

    public Cat(Long id, String nameCat, Integer ageCat) {
        this.id = id;
        this.nameCat = nameCat;
        this.ageCat = ageCat;
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

    public Integer getAgeCat() {
        return ageCat;
    }

    public void setAgeCat(Integer ageCat) {
        this.ageCat = ageCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return id.equals(cat.id) && nameCat.equals(cat.nameCat) && ageCat.equals(cat.ageCat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameCat, ageCat);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", nameCat='" + nameCat + '\'' +
                ", ageCat=" + ageCat +
                '}';
    }
}
