-- liquibase formatted sql

-- changeset kaloyan:1785441026135-1 splitStatements:false
CREATE TABLE User (id INT AUTO_INCREMENT NOT NULL, email VARCHAR(255) NULL, name VARCHAR(255) NULL, CONSTRAINT UserPK PRIMARY KEY (id));

