-- SkillSwap database schema (Oracle)
-- Run this once in SQL*Plus / SQL Developer as your schema user.

CREATE TABLE std (
    id      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name    VARCHAR2(100) NOT NULL UNIQUE,
    pass    VARCHAR2(100) NOT NULL,
    contact VARCHAR2(20)
);

CREATE TABLE skills (
    skill_id    NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    std_id      NUMBER NOT NULL,
    teach_skill VARCHAR2(100),
    learn_skill VARCHAR2(100),
    CONSTRAINT fk_skills_std FOREIGN KEY (std_id) REFERENCES std(id)
);

-- sample data (optional)
-- INSERT INTO std (name, pass, contact) VALUES ('Hari', 'hari123', '9876543210');
-- INSERT INTO skills (std_id, teach_skill, learn_skill) VALUES (1, 'Java', 'Python');
-- COMMIT;
