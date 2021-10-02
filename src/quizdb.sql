CREATE DATABASE quiz;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS topic;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS response;
DROP TABLE IF EXISTS user_question_history;
DROP TABLE IF EXISTS user_answer_history;

CREATE TABLE topic (
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (100) NOT NULL DEFAULT 'unknown'
);

CREATE TABLE question (
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    content VARCHAR (2000) NOT NULL DEFAULT 'unknown',
    difficulty INT (5) NOT NULL,
    topic_ID INT NOT NULL,
    FOREIGN KEY (topic_ID) REFERENCES topic(ID)
);

CREATE TABLE response (
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    answer VARCHAR (100) NOT NULL DEFAULT 'unknown',
    question_ID INT NOT NULL,
    FOREIGN KEY (question_ID) REFERENCES question(ID)
);
