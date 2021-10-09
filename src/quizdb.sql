CREATE DATABASE quiz;

CREATE TABLE topic
(
    ID   INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE question
(
    ID         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    content    VARCHAR(500) NOT NULL DEFAULT 'unknown',
    difficulty INT(5)       NOT NULL CHECK (difficulty BETWEEN 1 and 5),
    topic_ID   INT          NOT NULL,
    FOREIGN KEY (topic_ID) REFERENCES topic (ID) ON DELETE CASCADE
);

CREATE TABLE response
(
    ID          INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    answer      VARCHAR(100) NOT NULL DEFAULT 'unknown',
    question_ID INT          NOT NULL,
    FOREIGN KEY (question_ID) REFERENCES question (ID) ON DELETE CASCADE
);

INSERT INTO topic (name)
VALUES (LOWER('Sport')),
       (LOWER('Math')),
       (LOWER('Cars'));

INSERT INTO question (content, difficulty, topic_ID)
VALUES (LOWER('What sports do Michael Phelps?'), 2, 1),
       (LOWER('What is the opposite to differentiating?'), 5, 3),
       (LOWER('What is the year of Tesla company foundation?'), 2, 2);
INSERT INTO response (answer, question_ID)
VALUES (LOWER('Swimming'), 1),
       (LOWER('Integrating'), 2),
       (LOWER('2003'), 3);
