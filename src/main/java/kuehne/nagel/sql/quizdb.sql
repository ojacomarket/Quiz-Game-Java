CREATE DATABASE quiz;

USE quiz;

CREATE TABLE topic
(
    ID   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL DEFAULT 'unknown'
);

INSERT INTO topic (name)
VALUES (LOWER('Sport')),
       (LOWER('Math')),
       (LOWER('Cars'));

CREATE TABLE question
(
    ID         INT PRIMARY KEY AUTO_INCREMENT,
    content    VARCHAR(500) UNIQUE NOT NULL DEFAULT 'unknown',
    difficulty INT(5)              NOT NULL CHECK (difficulty BETWEEN 1 and 5),
    topic_ID   INT                 NOT NULL,
    FOREIGN KEY (topic_ID) REFERENCES topic (ID) ON DELETE CASCADE
);

INSERT INTO question (content, difficulty, topic_ID)
VALUES (LOWER('What sports do Michael Phelps?'), 2, 1),
       (LOWER('What is the opposite to differentiating?'), 4, 2),
       (LOWER('What is the year of Tesla company foundation?'), 5, 3);

INSERT INTO question (content, difficulty, topic_ID)
VALUES (LOWER('what is the formula for triangle perimeter?'), 2, 2),
       (LOWER('what is the sin of 90 degrees?'), 1, 2),
       (LOWER('what is the sum of all triangle angles?'), 4, 2);

CREATE TABLE response
(
    answer      VARCHAR(100) DEFAULT 'unknown',
    question_ID INT,
    PRIMARY KEY (answer, question_ID),
    FOREIGN KEY (question_ID) REFERENCES question (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO response (answer, question_ID)
VALUES (LOWER('Swimming'), 1),
       (LOWER('Integrating'), 2),
       (LOWER('2003'), 3);

