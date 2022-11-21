CREATE DATABASE bank;

USE bank;

CREATE TABLE users (
    account_number varchar(255) NOT NULL,
    pin int,
    balance int,
    overdraft int, PRIMARY KEY (account_number)
);

CREATE TABLE atms (
    atm_id int NOT NULL AUTO_INCREMENT,
    fifty int,
    twenty int,
    ten int,
    five int,
   PRIMARY KEY (atm_id)
);

INSERT INTO users (account_number, pin, balance, overdraft)
VALUES ("123456789", 1234, 800, 200);

INSERT INTO users (account_number, pin, balance, overdraft)
VALUES ("987654321", 4321, 1230, 150);

INSERT INTO atms(fifty, twenty, ten, five)
VALUES(10, 30, 30, 20);

