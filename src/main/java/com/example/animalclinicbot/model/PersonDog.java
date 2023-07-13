package com.example.animalclinicbot.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
/**
 * Класс с данными о владельце собаки
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person_dog")
public class PersonDog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //name пользователя
    @Column(name = "name")
    private String name;
    //yearOfBirth год рождения пользователя
    @Column(name = "yearOfBirth")
    private int yearOfBirth;
    //phone телефон пользователя
    @Column(name = "phone")
    private String phone;
    //mail електроная почта пользователя
    @Column(name = "email")
    private String mail;
    //address пользователя
    @Column(name = "address")
    private String address;

    //chat id пользователя
    @Column(name = "chat_id")
    private Long chatId;

    //связь OneToOne с собакой
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;

    public PersonDog(Long id, String name, Long chatId) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDog personDog = (PersonDog) o;
        return yearOfBirth == personDog.yearOfBirth && id.equals(personDog.id) && name.equals(personDog.name) && phone.equals(personDog.phone) && mail.equals(personDog.mail) && address.equals(personDog.address) && chatId.equals(personDog.chatId) && dog.equals(personDog.dog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, chatId, dog);
    }

    @Override
    public String toString() {
        return "PersonDog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chatId='" + chatId + '\'' +
                ", dog=" + dog +
                '}';
    }
}
