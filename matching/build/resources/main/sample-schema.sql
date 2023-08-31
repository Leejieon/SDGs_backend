-- Active: 1692088669467@@127.0.0.1@3306@testdb
DROP TABLE IF EXISTS learner;
CREATE TABLE learner(
    id BIGINT, 
    name VARCHAR(30),  
    email VARCHAR(30),
    strength VARCHAR(100),
    weakness VARCHAR(100),
    wish VARCHAR(100),
    purpose VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
);
 
DROP TABLE IF EXISTS coordinator;
 
CREATE TABLE coordinator (
    id BIGINT, 
    name VARCHAR(30),  
    email VARCHAR(30),
    field VARCHAR(100),
    strength VARCHAR(100),
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
);

DROP TABLE IF EXISTS matching;
 
CREATE TABLE coordinator (
    matching_id BIGINT,
    learner_id BIGINT,
    coordinator_id BIGINT
);