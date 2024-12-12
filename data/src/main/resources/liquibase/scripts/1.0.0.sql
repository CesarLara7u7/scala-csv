--liquibase formatted sql
--changeset Cesar:01
CREATE TABLE if not exists department
(
    id         integer PRIMARY KEY NOT NULL,
    department TEXT                NOT NULL
);
CREATE TABLE if not exists  job
(
    id  integer PRIMARY KEY NOT NULL,
    job TEXT                NOT NULL
);

CREATE TABLE if not exists  hired_employees
(
    id            integer PRIMARY KEY NOT NULL,
    name          TEXT                NOT NULL,
    datetime      VARCHAR(255)        NOT NULL,
    department_id integer             NOT NULL REFERENCES department (id),
    job_id        integer             NOT NULL REFERENCES job (id)
)