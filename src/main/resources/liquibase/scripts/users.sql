-- liquibase formatted sql
-- changeset galina:1

CREATE TABLE IF NOT EXISTS Dog(
     Id BIGINT,
     chatId VARCHAR,
     name_dog VARCHAR,
     breed VARCHAR,
     year_of_birth INT,
     description VARCHAR

);
