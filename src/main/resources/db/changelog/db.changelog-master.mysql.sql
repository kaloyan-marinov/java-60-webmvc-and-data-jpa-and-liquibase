-- liquibase formatted sql

-- changeset kaloyan:1785443916372-1 splitStatements:false
CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, email VARCHAR(255) NULL, name VARCHAR(255) NULL, CONSTRAINT userPK PRIMARY KEY (id));

-- changeset kaloyan:1785444468459-1 splitStatements:false
ALTER TABLE user ADD zip_code VARCHAR(255) NULL;

