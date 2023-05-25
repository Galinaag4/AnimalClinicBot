-- liquibase formatted sql
-- changeset stacey29:1
DROP TYPE IF EXISTS status CASCADE;


CREATE TABLE IF NOT EXISTS person_dog(
                                         id BIGSERIAL PRIMARY KEY,
                                         address VARCHAR,
                                         chat_id BIGINT,
                                         mail VARCHAR,
                                         name VARCHAR,
                                         phone VARCHAR,
                                         status VARCHAR(12),
                                         year_of_birth INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS report(
                                     id BIGSERIAL PRIMARY KEY,
                                     caption VARCHAR,
                                     chat_id BIGINT,
                                     data BYTEA,
                                     days BIGINT,
                                     file_path VARCHAR,
                                     file_size BIGINT NOT NULL ,
                                     habits VARCHAR,
                                     health VARCHAR,
                                     last_message DATE,
                                     ration VARCHAR

);

CREATE TABLE IF NOT EXISTS dog(
                                  id BIGSERIAL PRIMARY KEY,
                                  breed VARCHAR,
                                  description VARCHAR,
                                  name_dog VARCHAR,
                                  year_of_birth INTEGER NOT NULL ,
                                  person_id BIGSERIAL REFERENCES person_dog (id),
                                  report_id BIGSERIAL REFERENCES report (id)

);

