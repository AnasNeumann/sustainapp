-- creation du schema prevu dans le digramme de classes initial
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 9/01/2017

-- ********************************************************************************************
-- TABLES PRINCIPALES
-- ********************************************************************************************

CREATE TABLE PROFILE (
    ID SERIAL PRIMARY KEY,
    LAST_NAME VARCHAR(150),
    FIRST_NAME VARCHAR(150),
    MAIL VARCHAR(150) UNIQUE NOT NULL,
    PASSWORD VARCHAR(150) NOT NULL,
    BORN_DATE TIMESTAMP,
    COVER BYTEA NULL,
    AVATAR BYTEA NULL,
    IS_ADMIN INTEGER NOT NULL DEFAULT 0,
    TIMESTAMPS TIMESTAMP
);

-- ********************************************************************************************
-- PARTIE COURS ET CERTIFICATS [release 1]
-- ********************************************************************************************

CREATE TABLE CATEGORY (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    ICON BYTEA NULL,
    TIMESTAMPS TIMESTAMP
);

CREATE TABLE COURSE (
    ID SERIAL PRIMARY KEY,
    TITLE VARCHAR(150),
    ABOUT TEXT,
    PICTURE BYTEA NULL,
    CATEGORY_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CREATOR_ID INTEGER NOT NULL,
    CONSTRAINT FK_COURSE_CREATOR_ID  FOREIGN KEY (CREATOR_ID) REFERENCES PROFILE (ID),
    CONSTRAINT PF_COURSE_CATEGORY_ID FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (ID)
);

CREATE TABLE RANK_COURSE (
    ID SERIAL PRIMARY KEY,
    SCORE INTEGER NOT NULL DEFAULT 0,
    PROFILE_ID  INTEGER NOT NULL,
    COURSE_ID  INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_RANK_COURSE_PROFILE_ID FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK__RANK_COURSE_COURSE_ID FOREIGN KEY (COURSE_ID) REFERENCES COURSE (ID)
);

CREATE TABLE TOPIC(
    ID SERIAL PRIMARY KEY,
    TITLE VARCHAR(150),
    CONTENT TEXT,
    PICTURE BYTEA NULL,
    LINK VARCHAR(150),
    COURSE_ID  INTEGER,
    DIFFICULTY INTEGER NOT NULL DEFAULT 0,
    CHILD_LEVEL INTEGER NOT NULL DEFAULT 0,
    PARENT_ID INTEGER,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK__TOPIC_COURSE_ID FOREIGN KEY (COURSE_ID) REFERENCES COURSE (ID),
    CONSTRAINT FK__TOPIC_PARENT_ID FOREIGN KEY (PARENT_ID) REFERENCES TOPIC (ID)
);

CREATE TABLE TOPIC_VALIDATION(
    ID SERIAL PRIMARY KEY,
    PROFILE_ID  INTEGER NOT NULL,
    COURSE_ID  INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_TOPIC_VALIDATION_PROFILE_ID FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK__TOPIC_VALIDATION_COURSE_ID FOREIGN KEY (COURSE_ID) REFERENCES COURSE (ID)
);

CREATE TABLE QUESTION(
    ID SERIAL PRIMARY KEY,
    TOPIC_ID INTEGER NOT NULL,
    MESSAGE TEXT,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_QUESTION_TOPIC_ID FOREIGN KEY (TOPIC_ID) REFERENCES TOPIC (ID)
);

CREATE TABLE ANSWER(
    ID SERIAL PRIMARY KEY,
    QUESTION_ID INTEGER NOT NULL,
    MESSAGE TEXT,
    IS_TRUE INTEGER NOT NULL DEFAULT 0,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_ANSWER_QUESTION_ID FOREIGN KEY (QUESTION_ID) REFERENCES QUESTION (ID)
);

-- ********************************************************************************************
-- PARTIE TEAM [release 2]
-- ********************************************************************************************

CREATE TABLE TEAM(
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(150),
	LEVEL INTEGER NOT NULL DEFAULT 0,
	TIMESTAMPS TIMESTAMP
);

CREATE TABLE TEAM_ROLE(
	ID SERIAL PRIMARY KEY,
	ROLE VARCHAR(50),
	TEAM_ID INTEGER NOT NULL,
	PROFILE_ID INTEGER NOT NULL,
	TIMESTAMPS TIMESTAMP,
	CONSTRAINT FK_TEAM_ROLE_TEAM_ID FOREIGN KEY (TEAM_ID) REFERENCES TEAM (ID),
	CONSTRAINT FK_TEAM_ROLE_PROFILE_ID FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID)
);

-- ********************************************************************************************
-- PARTIE CHALLENGE [release 3]
-- ********************************************************************************************

CREATE TABLE CHALLENGE_TYPE (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    ICON BYTEA NULL,
    TIMESTAMPS TIMESTAMP
);

CREATE TABLE CHALLENGE (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    ABOUT TEXT,
    END_DATE TIMESTAMP,
    ICON BYTEA NULL,
    TEAM_ENABLED INTEGER NOT NULL DEFAULT 0,
    CHALLENGE_TYPE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CREATOR_ID INTEGER NOT NULL,
    CONSTRAINT FK_CHALLENGE_CREATOR_ID  FOREIGN KEY (CREATOR_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK_CHALLENGE_CHALLENGE_TYPE_ID  FOREIGN KEY (CHALLENGE_TYPE_ID) REFERENCES CHALLENGE_TYPE (ID)
);

CREATE TABLE PARTICIPATION(
    ID SERIAL PRIMARY KEY,
    TITLE VARCHAR(150),
    ABOUT TEXT,
    DOCUMENT BYTEA NULL,
    TARGET_ID INTEGER NOT NULL,
    TARGET_TYPE INTEGER NOT NULL,
    CHALLENGE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_PARTICIPATION_CHALLENGE_ID FOREIGN KEY (CHALLENGE_ID) REFERENCES CHALLENGE (ID)
);

CREATE TABLE CHALLENGE_VOTE(
    ID SERIAL PRIMARY KEY,
    SCORE INTEGER NOT NULL DEFAULT 0,
    PROFILE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CHALLENGE_ID INTEGER NOT NULL,
    CONSTRAINT FK_CHALLENGE_VOTE_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK_CHALLENGE_VOTE_CHALLENGE_ID FOREIGN KEY (CHALLENGE_ID) REFERENCES CHALLENGE (ID)
);

-- ********************************************************************************************
-- PARTIE BADGES [release 4]
-- ********************************************************************************************

CREATE TABLE BADGE(
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    SCORE INTEGER NOT NULL DEFAULT 0,
    ICON BYTEA NULL,
    TIMESTAMPS TIMESTAMP
);

CREATE TABLE PROFILE_BADGE(
    ID SERIAL PRIMARY KEY,
    PROFILE_ID INTEGER NOT NULL,
    BADGE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_PROFILE_BADGE_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK_PROFILE_BADGE_BADGE_ID  FOREIGN KEY (BADGE_ID) REFERENCES BADGE (ID)
);

-- ********************************************************************************************
-- PARTIE NEWS [release 5]
-- ********************************************************************************************

CREATE TABLE NEWS(
    ID SERIAL PRIMARY KEY,
    TITLE VARCHAR(150),
    CONTENT TEXT,
    PICTURE BYTEA NULL,
    TIMESTAMPS TIMESTAMP
);

CREATE TABLE READ_NEWS(
    ID SERIAL PRIMARY KEY,
    PROFILE_ID INTEGER NOT NULL,
    NEWS_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_READ_NEWS_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK_READ_NEWS_NEWS_ID  FOREIGN KEY (NEWS_ID) REFERENCES NEWS (ID)
);

-- ********************************************************************************************
-- PARTIE TRANSPORT [release 5]
-- ********************************************************************************************

CREATE TABLE TRANSPORT(
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    ENERGICAL_VALUE INTEGER NOT NULL DEFAULT 0,
    ICON BYTEA NULL,
    TIMESTAMPS TIMESTAMP
);

CREATE TABLE TRAVEL(
    ID SERIAL PRIMARY KEY,
    PROFILE_ID INTEGER NOT NULL,
    TRANSPORT_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_TRAVEL_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK_TRAVEL_TRANSPORT_ID  FOREIGN KEY (TRANSPORT_ID) REFERENCES TRANSPORT (ID)
);

-- ********************************************************************************************
-- AUTRES TABLES [other releases]
-- ********************************************************************************************

CREATE TABLE AWARD(
    ID SERIAL PRIMARY KEY,
    ABOUT TEXT,
    PROFILE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_AWARD_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID)
);

CREATE TABLE REPORT(
    ID SERIAL PRIMARY KEY,
    MESSAGE TEXT,
    DOCUMENT BYTEA NULL,
    DOCUMENT_TYPE VARCHAR(50),
    STATE INTEGER NOT NULL DEFAULT 0,
    PROFILE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_REPORT_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID)
);

CREATE TABLE NOTIFICATION(
    ID SERIAL PRIMARY KEY,
    MESSAGE VARCHAR(150),
    LINK_TYPE INTEGER NOT NULL,
    LINK_ID INTEGER NOT NULL,
    STATE INTEGER NOT NULL DEFAULT 0,
    CREATOR_ID INTEGER NOT NULL,
    PROFILE_ID INTEGER NOT NULL,
    ACTOR_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_NOTIFICATION_CREATOR_ID  FOREIGN KEY (CREATOR_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK_NOTIFICATION_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID)
);

CREATE TABLE MISSION(
    ID SERIAL PRIMARY KEY,
    STATE INTEGER NOT NULL DEFAULT 0,
    MESSAGE TEXT,
    CREATOR_ID INTEGER NOT NULL,
    PROFILE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK_MISSION_CREATOR_ID  FOREIGN KEY (CREATOR_ID) REFERENCES PROFILE (ID),
    CONSTRAINT FK_MISSION_PROFILE_ID  FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID)
);


