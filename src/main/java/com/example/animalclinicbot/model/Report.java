package com.example.animalclinicbot.model;

import liquibase.repackaged.net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
/**
 * Класс отчета по животному
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long chatId;
    private String name;
    private String ration;
    private String health;
    private String behaviour;
    private Date lastMessage;
    @Lob
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