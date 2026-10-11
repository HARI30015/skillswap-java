SkillSwap - SQLite version (no Oracle needed)
=============================================
Same Swing app as the Oracle version, but the database is SQLite:
a single file (skillswap.db) created automatically on first run.
Nothing to install, no server, no setup.

FILES
-----
DBConnection.java    - SQLite connection (jdbc:sqlite:skillswap.db)
DBInit.java          - creates the std + skills tables on first run
schema_sqlite.sql   - the table definitions (for reference)
SkillSwapApp.java    - entry point
LoginFrame.java      - login screen (checks std table)
SignupFrame.java     - sign-up screen (inserts into std)
DashboardFrame.java  - dashboard: Add Skill | View Students | Search Skill
sqlite-jdbc.jar, slf4j-api.jar, slf4j-simple.jar - database driver (included)

TABLES (auto-created)
---------------------
std    (id, name, pass, contact)
skills (skill_id, std_id, teach_skill, learn_skill)

RUN (Linux / Mac)
-----------------
javac -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar *.java
java  -cp .:sqlite-jdbc.jar:slf4j-api.jar:slf4j-simple.jar SkillSwapApp

RUN (Windows - use ; instead of :)
---------------------------------
javac -cp .;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar *.java
java  -cp .;sqlite-jdbc.jar;slf4j-api.jar;slf4j-simple.jar SkillSwapApp

Then: Sign Up a new user -> Login -> Add Skill -> View Students -> Search Skill.
Your data is stored in the skillswap.db file next to the program.
Delete that file to start fresh.
