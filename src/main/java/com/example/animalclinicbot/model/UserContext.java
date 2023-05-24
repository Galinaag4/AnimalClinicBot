package com.example.animalclinicbot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class UserContext {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * номер чата пользователя
     */
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    /**
     * имя пользователя
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * выбор приюта
     */
    @Column(name = "is_cat_shelter", nullable = false)
    private boolean isCatShelter = true;

    public UserContext(Long id, String name, boolean isCatShelter) {
        this.id = id;
        this.name = name;
        this.isCatShelter = isCatShelter;
    }

    public UserContext() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCatShelter() {
        return isCatShelter;
    }

    public void setCatShelter(boolean catShelter) {
        isCatShelter = catShelter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserContext that = (UserContext) o;
        return isCatShelter == that.isCatShelter && id.equals(that.id) && chatId.equals(that.chatId) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, isCatShelter);
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", isCatShelter=" + isCatShelter +
                '}';
    }
}



