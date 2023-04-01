package com.example.animalclinicbot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
/**
 * Класс отчета по животному
 */
@Entity
public class Report {
    /**
     * идентификатор записи
     */
    @Id
    @GeneratedValue
    private long id;
    /**
     * идентификатор чата в телеграмм
     */
    private Long chatId;
    /**
     * рацион питомца
     */
    private String ration;
    /**
     * здоровье питомца
     */
    private String health;
    /**
     * привычки животного
     */
    private String habits;
    /**
     * время испытательного срока
     */
    private long days;
    /**
     * путь к фото
     */
    private String filePath;
    /**
     * размер фото
     */
    private long fileSize;
    /**
     * сам файл, массив байт
     */
    @Lob
    private byte[] data;
    /**
     * подпись к фото
     */
    private String caption;
    /**
     * дата последнего сообщения
     */
    private Date lastMessage;


    public Report(long id, Long chatId, String ration, String health, String habits, long days, String filePath, long fileSize, byte[] data, String caption, Date lastMessage) {
        this.id = id;
        this.chatId = chatId;
        this.ration = ration;
        this.health = health;
        this.habits = habits;
        this.days = days;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.data = data;
        this.caption = caption;
        this.lastMessage = lastMessage;

    }

    public Report() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHabits() {
        return habits;
    }

    public void setHabits(String habits) {
        this.habits = habits;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Date lastMessage) {
        this.lastMessage = lastMessage;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report report)) return false;
        return getId() == report.getId() && getDays() == report.getDays() && getFileSize() == report.getFileSize() && Objects.equals(getChatId(), report.getChatId()) && Objects.equals(getRation(), report.getRation()) && Objects.equals(getHealth(), report.getHealth()) && Objects.equals(getHabits(), report.getHabits()) && Objects.equals(getFilePath(), report.getFilePath()) && Arrays.equals(getData(), report.getData()) && Objects.equals(getCaption(), report.getCaption()) && Objects.equals(getLastMessage(), report.getLastMessage());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getChatId(), getRation(), getHealth(), getHabits(), getDays(), getFilePath(), getFileSize(), getCaption(), getLastMessage());
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", ration='" + ration + '\'' +
                ", health='" + health + '\'' +
                ", habits='" + habits + '\'' +
                ", days=" + days +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", data=" + Arrays.toString(data) +
                ", caption='" + caption + '\'' +
                ", lastMessage=" + lastMessage +
                '}';
    }
}
