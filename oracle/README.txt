SkillSwap - Swing + Oracle JDBC Project
=====================================

FILES
-----
DBConnection.java    - Oracle database connection (EDIT the URL/username/password)
schema.sql           - Table creation script (std + skills)
SkillSwapApp.java    - Entry point, opens the login screen
LoginFrame.java      - Login screen (checks name + pass in std table)
SignupFrame.java     - New-user registration (inserts into std table)
DashboardFrame.java  - Main window after login with 3 tabs:
                       Add Skill | View Students | Search Skill

TABLES
------
std    (id, name, pass, contact)
skills (skill_id, std_id, teach_skill, learn_skill)

SETUP (on the machine with Oracle installed)
-------------------------------------------
1. Run schema.sql once in SQL*Plus or SQL Developer:
       sqlplus system/tiger @schema.sql
   (use XE service name "XE" or change it in DBConnection.java)

2. In DBConnection.java, set DB_URL / DB_USER / DB_PASS to your values.
   Common Oracle XE URL: jdbc:oracle:thin:@localhost:1521:XE

3. The Oracle JDBC driver (ojdbc11.jar) is INCLUDED in this folder,
   so you can skip downloading it.

4. Compile (the driver jar must be on the classpath):
       javac -cp .;ojdbc11.jar *.java
   On Linux/Mac use ':' instead of ';':
       javac -cp .:ojdbc11.jar *.java

5. Run:
       java -cp .;ojdbc11.jar SkillSwapApp

HOW IT WORKS
------------
- Login screen -> SELECT id,name FROM std WHERE name=? AND pass=?
- Sign Up      -> INSERT INTO std (name, pass, contact)
- Add Skill    -> INSERT INTO skills (std_id, teach_skill, learn_skill)
- View         -> SELECT ... FROM std LEFT JOIN skills (shown in a JTable)
- Search       -> same query with WHERE UPPER(teach_skill) = UPPER(?)

All database queries use PreparedStatement (safe from SQL injection).
