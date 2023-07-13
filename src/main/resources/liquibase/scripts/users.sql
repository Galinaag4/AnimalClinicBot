-- liquibase formatted sql
-- changeset stacey29:1


CREATE TABLE dog
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR,
    breed         VARCHAR,
    year_of_birth INTEGER,
    description   VARCHAR
);

CREATE TABLE cat
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR,
    breed         VARCHAR,
    year_of_birth INTEGER,
    description   VARCHAR
);

CREATE TABLE person_cat
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR,
    year_of_birth INTEGER,
    phone         VARCHAR,
    mail          VARCHAR,
    address       VARCHAR,
    chat_id       BIGINT,
    cat_id        BIGINT REFERENCES cat (id),
    status        VARCHAR
);

CREATE TABLE person_dog
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR,
    year_of_birth INTEGER,
    phone         VARCHAR,
    email         VARCHAR,
    address       VARCHAR,
    chat_id       BIGINT,
    dog_id        BIGINT REFERENCES dog (id),
    status        VARCHAR
);

CREATE TABLE user_context
(
    chat_id         BIGSERIAL PRIMARY KEY,
    type_of_shelter INTEGER,
    person_cat_id   BIGINT REFERENCES person_cat (id),
    person_dog_id   BIGINT REFERENCES person_dog (id)
);
CREATE TABLE report
(
    id           BIGSERIAL PRIMARY KEY,
    chat_id      BIGINT,
    name         VARCHAR,
    ration       VARCHAR,
    health       VARCHAR,
    behaviour    VARCHAR,
    last_message TIMESTAMP,
    data         oid

);
