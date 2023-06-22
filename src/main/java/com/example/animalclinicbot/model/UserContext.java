package com.example.animalclinicbot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class UserContext {
    /**
     * номер чата пользователя
     */
    @Id
    private Long chatId;

    private boolean catShelter;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isCatShelter() {
        return catShelter;
    }

    public void setCatShelter(boolean catShelter) {
        this.catShelter = catShelter;
    }
}