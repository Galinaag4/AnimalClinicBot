package com.example.animalclinicbot.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Класс с данными о владельце кота
 */
@Entity
@Table (name = "personCat")
public class PersonCat {

    /**
     * идентификатор записи
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * имя хозяина животного
     */
    @Column(name = "name_person_cat", nullable = false)
    private String namePersonCat;

    /**
     * дата рождения владельца
     */
    @Column(name = "year_of_birth_person_cat", nullable = false)
    private int yearOfBirthPersonCat;

    /**
     * номер телефона хозяина животного
     */
    @Column(name = "phone_person_cat", nullable = false)
    private String phonePersonCat;

    /**
     * email хозяина животного
     */
    @Column(name = "mail_person_cat", nullable = false)
    private String mailPersonCat;

    /**
     * адрес хозяина животного
     */
    @Column(name = "address_person_cat", nullable = false)
    private String addressPersonCat;

    /**
     * идентификатор чата в телеграмм
     */

    private Long chatId;

    /**
     * статус нахождения животного
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status_cat", nullable = false)
    private Status statusCat;

    @OneToMany (mappedBy = "personCat")
    @JsonManagedReference
    private List <Cat> cats;

    /**
     * ID отчета
     */

    @OneToOne(orphanRemoval = true)
    @JoinTable(name = "person_cat_report",
            joinColumns = @JoinColumn(name = "person_cat_null"),
            inverseJoinColumns = @JoinColumn(name = "report_id"))
    @JoinColumn(name = "id")
    private Report report;

    public PersonCat(Long id,String namePersonCat, int yearOfBirthPersonCat, String phonePersonCat,
                     String mailPersonCat, String addressPersonCat, Long chatIdPersonCat,
                     Status statusCat, List<Cat> cats,Long chatId, Report report) {
        this.id = id;
        this.namePersonCat = namePersonCat;
        this.yearOfBirthPersonCat = yearOfBirthPersonCat;
        this.phonePersonCat = phonePersonCat;
        this.mailPersonCat = mailPersonCat;
        this.addressPersonCat = addressPersonCat;
        this.chatId = chatId;
        this.statusCat = statusCat;
        this.cats = cats;
        this.report = report;
    }

    public PersonCat(long l, String petr, int i, String s, String s1, String moscow, int i1, Status search) {

    }

    public PersonCat(String namePersonCat) {
        this.namePersonCat = namePersonCat;
    }

    public PersonCat(Long id, String namePersonCat) {
        this.id = id;
        this.namePersonCat = namePersonCat;
    }

    public PersonCat(String name, String phone, long finalChatId) {

    }

    public PersonCat() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamePersonCat() {
        return namePersonCat;
    }

    public void setNamePersonCat(String namePersonCat) {
        this.namePersonCat = namePersonCat;
    }

    public int getYearOfBirthPersonCat() {
        return yearOfBirthPersonCat;
    }

    public void setYearOfBirthPersonCat(int yearOfBirthPersonCat) {
        this.yearOfBirthPersonCat = yearOfBirthPersonCat;
    }

    public String getPhonePersonCat() {
        return phonePersonCat;
    }

    public void setPhonePersonCat(String phonePersonCat) {
        this.phonePersonCat = phonePersonCat;
    }

    public String getMailPersonCat() {
        return mailPersonCat;
    }

    public void setMailPersonCat(String mailPersonCat) {
        this.mailPersonCat = mailPersonCat;
    }

    public String getAddressPersonCat() {
        return addressPersonCat;
    }

    public void setAddressPersonCat(String addressPersonCat) {
        this.addressPersonCat = addressPersonCat;
    }

    public Long getChatIdPersonCat() {
        return chatId;
    }

    public void setChatIdPersonCat(Long chatIdt) {
        this.chatId = chatId;
    }

    public Status getStatusCat() {
        return statusCat;
    }

    public void setStatusCat(Status statusCat) {
        this.statusCat = statusCat;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonCat personCat = (PersonCat) o;
        return yearOfBirthPersonCat == personCat.yearOfBirthPersonCat && id.equals(personCat.id)
                && namePersonCat.equals(personCat.namePersonCat) &&
                phonePersonCat.equals(personCat.phonePersonCat) && mailPersonCat.equals(personCat.mailPersonCat)
                && addressPersonCat.equals(personCat.addressPersonCat) &&
                chatId.equals(personCat.chatId) &&
                statusCat == personCat.statusCat && cats.equals(personCat.cats) &&
                report.equals(personCat.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namePersonCat, yearOfBirthPersonCat, phonePersonCat,
                mailPersonCat, addressPersonCat, chatId, statusCat, cats, report);
    }

    @Override
    public String toString() {
        return "PersonCat{" +
                "id=" + id +
                ", namePersonCat='" + namePersonCat + '\'' +
                ", yearOfBirthPersonCat=" + yearOfBirthPersonCat +
                ", phonePersonCat='" + phonePersonCat + '\'' +
                ", mailPersonCat='" + mailPersonCat + '\'' +
                ", addressPersonCat='" + addressPersonCat + '\'' +
                ", chatIdPersonCat=" + chatId +
                ", statusCat=" + statusCat +
                ", cats=" + cats +
                ", report=" + report +
                '}';
    }
}
