package com.example.animalclinicbot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;
/**
 * Класс с данными о владельце животного
 */
@Entity
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
    private String name;
    /**
     * дата рождения владельца
     */
    private int yearOfBirth;
    /**
     * номер телефона хозяина животного
     */
    private String phone;
    /**
     * email хозяина животного
     */
    private String mail;
    /**
     * адрес хозяина животного
     */
    private String address;
    /**
     * идентификатор чата в телеграмм
     */
    private Long chatId;
    /**
     * статус нахождения животного
     */
    private Status status;
    /**
     * ID питомца
     */
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;
    /**
     * ID отчета
     */
    @OneToOne(orphanRemoval = true)
    @JoinTable(name = "person_report_data",
            joinColumns = @JoinColumn(name = "person_null"),
            inverseJoinColumns = @JoinColumn(name = "report_data_id"))
    private Report report;

    public PersonDog(Long id, String name, int yearOfBirth, String phone, String mail, String address, Long chatId, Status status, Dog dog, Report report) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.chatId = chatId;
        this.status = status;
        this.dog = dog;
        this.report = report;
    }

    public PersonDog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDog personDog)) return false;
        return getYearOfBirth() == personDog.getYearOfBirth() && Objects.equals(getId(), personDog.getId()) && Objects.equals(getName(), personDog.getName()) && Objects.equals(getPhone(), personDog.getPhone()) && Objects.equals(getMail(), personDog.getMail()) && Objects.equals(getAddress(), personDog.getAddress()) && Objects.equals(getChatId(), personDog.getChatId()) && getStatus() == personDog.getStatus() && Objects.equals(getDog(), personDog.getDog()) && Objects.equals(getReport(), personDog.getReport());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYearOfBirth(), getPhone(), getMail(), getAddress(), getChatId(), getStatus(), getDog(), getReport());
    }

    @Override
    public String toString() {
        return "PersonDog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", chatId=" + chatId +
                ", status=" + status +
                ", dog=" + dog +
                ", report=" + report +
                '}';
    }
}
