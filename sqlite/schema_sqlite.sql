-- SkillSwap database schema (SQLite version)
-- No need to run this by hand: the app creates the file automatically,
-- but you can run it with:  sqlite3 skillswap.db < schema_sqlite.sql

CREATE TABLE IF NOT EXISTS std (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    name    TEXT NOT NULL UNIQUE,
    pass    TEXT NOT NULL,
    contact TEXT
);

CREATE TABLE IF NOT EXISTS skills (
    skill_id    INTEGER PRIMARY KEY AUTOINCREMENT,
    std_id      INTEGER NOT NULL,
    teach_skill TEXT,
    learn_skill TEXT,
    FOREIGN KEY (std_id) REFERENCES std(id)
);
