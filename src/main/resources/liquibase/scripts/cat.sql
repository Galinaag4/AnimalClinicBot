-- liquibase formatted sql
-- changeset scherbakova:1

DROP TYPE IF EXISTS status CASCADE;
CREATE TABLE if not exists user_context
(
    chat_id     BIGINT PRIMARY KEY,
    cat_shelter BOOLEAN,
    dog_shelter BOOLEAN
);
CREATE TABLE IF NOT EXISTS person_cat(
                                         id BIGSERIAL PRIMARY KEY,
                                         address_person_cat VARCHAR,
                                         chat_id_person_cat BIGINT,
                                         mail_person_cat VARCHAR,
                                         name_person_cat VARCHAR,
                                         phone_person_cat VARCHAR,
                                         status_cat VARCHAR(12),
                                         year_of_birth_person_cat INTEGER NOT NULL
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

CREATE TABLE IF NOT EXISTS Cat(
                                  id BIGSERIAL PRIMARY KEY,
                                  breed_cat VARCHAR,
                                  description_cat VARCHAR,
                                  name_cat VARCHAR,
                                  year_of_birth_cat INTEGER NOT NULL ,
                                  personCat_id BIGSERIAL REFERENCES person_cat (id),
                                  report_id BIGSERIAL REFERENCES report (id)

);
ALTER TABLE report ALTER COLUMN data TYPE bytea USING data::bytea;
ALTER TABLE report ALTER COLUMN last_message TYPE TIMESTAMP;


