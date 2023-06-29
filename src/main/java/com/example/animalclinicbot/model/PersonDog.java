package com.example.animalclinicbot.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Класс с данными о владельце собаки
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person_dog")
public class PersonDog {
    /**
     * идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * имя хозяина животного
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * дата рождения владельца
     */
    @Column(name = "year_of_birth", nullable = false)
    private int yearOfBirth;
    /**
     * номер телефона хозяина животного
     */

    @Column(name = "phone", nullable = false)
    private String phone;
    /**
     * email хозяина животного
     */
    @Column(name = "mail", nullable = false)
    private String mail;
    /**
     * адрес хозяина животного
     */
    @Column(name = "address", nullable = false)
    private String address;
    /**
     * идентификатор чата в телеграмм
     */
    @Column(name = "chat_id", nullable = false)
    private Long chatId;
    /**
     * статус нахождения животного
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    //связь OneToOne с собакой
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;

    public PersonDog(Long id, String name, Long chatId) {
        this.id = id;
        this.name = name;
        this.chatId = chatId;
    }
}