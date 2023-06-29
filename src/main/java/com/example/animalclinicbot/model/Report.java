package com.example.animalclinicbot.model;

import liquibase.repackaged.net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Класс отчета по животному
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Report {
    @Id
    @GeneratedValue

    private long id;
    private Long chatId;
    private String name;
    private String ration;
    private String health;
    private String behaviour;
    private Date lastMessage;
    @Lob
    @Column (columnDefinition = "bytea", name = "data", nullable = false)
    private byte[] data;

    public Report(Long chatId, String name, String ration, String health, String behaviour, Date lastMessage, byte[] data) {
        this.chatId = chatId;
        this.name = name;
        this.ration = ration;
        this.health = health;
        this.behaviour = behaviour;
        this.lastMessage = lastMessage;
        this.data = data;
    }
}
