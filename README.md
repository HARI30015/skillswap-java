# SkillSwap

A Java **Swing** desktop application where students register, list the skills they can **teach** and the skills they want to **learn**, browse other students, and search by skill. Data is stored in a real database via **JDBC**.

Two flavours included: **SQLite** (zero setup, runs anywhere) and **Oracle** (college-submission version).

---

## Features

- 🔐 **Login screen** — username + password checked against the `std` table
- 📝 **Sign up** — new users registered into `std` (duplicate usernames rejected)
- ➕ **Add Skill** — save "can teach" / "wants to learn" into the `skills` table
- 👀 **View Students** — everyone listed in a table (join of `std` + `skills`)
- 🔎 **Search Skill** — find who teaches a skill (case-insensitive)
- 🛡️ All queries use `PreparedStatement` (no SQL injection)

## Database schema

```sql
std    (id, name, pass, contact)
skills (skill_id, std_id, teach_skill, learn_skill)
```

## Quick start — SQLite version (recommended, no setup)

Needs JDK 17. Download the SQLite JDBC driver once:

```bash
curl -sL -o sqlite-jdbc.jar https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.45.3.0/sqlite-jdbc-3.45.3.0.jar
curl -sL -o slf4j-api.jar   https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar
curl -sL -o slf4j-simple.jar https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar
```

Then compile and run from the `sqlite/` folder:

```bash
# Linux / Mac
javac -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar *.java
java  -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar SkillSwapApp

# Windows (use ; instead of :)
javac -cp .;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar *.java
java  -cp .;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar SkillSwapApp
```

The database file `skillswap.db` and both tables are created automatically on first run.

## Verified output (real run, 2026-10-11)

Compile — clean, zero errors:

```
$ javac -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar *.java
$ echo $?
0
```

Full end-to-end run against a real database (driven through the actual GUI):

```
[Login screen opens]
  -> click "New user? Sign Up"
[Sign Up screen opens]
  -> Username: demouser | Password: demo123 | Confirm: demo123 | Contact: 9876543210
  -> click Register
  => popup: "Registration successful! Please login."
[back to Login screen]
  -> Username: demouser | Password: demo123
  -> click Login
  => dashboard opens: "Welcome, demouser!"
[Add Skill tab]
  -> Teach: Java | Learn: Python
  -> click Save
  => popup: "Skill saved!"
[View Students tab]
  => Name      | Contact    | Can Teach | Wants to Learn
     demouser  | 9876543210 |           |
[Search Skill tab]
  -> Skill: java -> click Search
  => Name      | Contact    | Can Teach | Wants to Learn
     demouser  | 9876543210 | java      | Python
```

Database contents after the run (`std` ⨝ `skills`):

| name     | contact    | teach_skill | learn_skill |
|----------|------------|-------------|-------------|
| demouser | 9876543210 | java        | Python      |

Every step above — signup, login, add skill, view, search — executed against
a real database and confirmed working.

## Oracle version (college submission)

The `oracle/` folder is the same app wired to Oracle Database:

1. Run `oracle/schema.sql` in SQL*Plus / SQL Developer (creates `std` + `skills`)
2. Set your Oracle username/password in `oracle/DBConnection.java`
3. Get `ojdbc11.jar` (oracle.com or your Oracle install) and run:

```bash
javac -cp .:ojdbc11.jar *.java
java  -cp .:ojdbc11.jar SkillSwapApp
```

## Project structure

```
skillswap-java/
├── README.md            # this homepage
├── sqlite/              # runnable version (SQLite, zero setup)
│   ├── SkillSwapApp.java
│   ├── LoginFrame.java
│   ├── SignupFrame.java
│   ├── DashboardFrame.java
│   ├── DBConnection.java
│   ├── DBInit.java
│   └── schema_sqlite.sql
└── oracle/              # college version (Oracle JDBC)
    ├── SkillSwapApp.java
    ├── LoginFrame.java
    ├── SignupFrame.java
    ├── DashboardFrame.java
    ├── DBConnection.java
    └── schema.sql
```

## Tech stack

Java 17 · Swing · JDBC · SQLite / Oracle
