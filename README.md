# SkillSwap

A Java **Swing** desktop application where students register, list the skills they can **teach** and the skills they want to **learn**, browse other students, and search by skill. Data is stored in a real database via **JDBC**.

Two flavours included: **SQLite** (zero setup, runs anywhere) and **Oracle** (college-submission version).

---

## Screenshots (real run)

**Login screen**
![Login](screenshots/01-login.png)

**Sign up**
![Sign up](screenshots/02-signup.png)

**Registration successful**
![Registered](screenshots/03-registered.png)

**Dashboard after login**
![Dashboard](screenshots/04-dashboard.png)

**View students (data from the database)**
![View students](screenshots/05-view-students.png)

**Search skill**
![Search](screenshots/06-search.png)

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

### Sample terminal output

```
$ javac -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar *.java
$ java  -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar SkillSwapApp
# login window opens -> sign up "demouser" -> Registration successful!
# login -> Welcome, demouser! -> add skill Java/Python -> Skill saved!
# search "java" -> demouser | 9876543210 | java | Python
```

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
├── screenshots/         # app screenshots
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
