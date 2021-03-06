-- supression d'un badge inutilisé ainsi que tables inutilisées
DROP TABLE IF EXISTS READ_NEWS CASCADE;
DROP TABLE IF EXISTS NEWS CASCADE;
DELETE FROM BADGE WHERE ABOUT = 'badges.journalist.about';

-- Modification de la table user pour connaitre s'il possède une ville ou non
ALTER TABLE USER_ACCOUNT
ADD USER_TYPE INTEGER DEFAULT 0;

-- Ajout des tables nécéssaires 
-- Une ville
CREATE TABLE CITY (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    ABOUT TEXT,
    ACTIF INTEGER DEFAULT 0,
    COVER BYTEA NULL,
    TIMESTAMPS TIMESTAMP,
    USER_ACCOUNT_ID INTEGER NOT NULL,
    CONSTRAINT FK__CITY_USER_ACCOUNT_ID FOREIGN KEY (USER_ACCOUNT_ID) REFERENCES USER_ACCOUNT (ID)
);

-- Une ville possède plusieurs lieux
CREATE TABLE PLACE (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    ABOUT TEXT,
    ADDRESS VARCHAR(500),
    LONGITUDE INTEGER DEFAULT 0,
    LATITUDE INTEGER DEFAULT 0,
    TIMESTAMPS TIMESTAMP,
    CITY_ID INTEGER NOT NULL,
    CONSTRAINT FK__PLACE_CITY_ID FOREIGN KEY (CITY_ID) REFERENCES CITY (ID)
);

-- Un lieu possède plusieurs images
CREATE TABLE PLACE_PICTURE (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(150),
    ABOUT TEXT,
    DOCUMENT BYTEA NULL,
    TIMESTAMPS TIMESTAMP,
    PLACE_ID INTEGER NOT NULL,
    CONSTRAINT FK__PLACE_PICTURE_ID FOREIGN KEY (PLACE_ID) REFERENCES PLACE (ID)
);

-- Un lieu possède également plusieurs notes
CREATE TABLE PLACE_NOTE(
    ID SERIAL PRIMARY KEY,
    PLACE_ID INTEGER NOT NULL,
    PROFILE_ID INTEGER NOT NULL,
    SCORE INTEGER DEFAULT 0,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK__PLACE_NOTE_PLACE_ID FOREIGN KEY (PLACE_ID) REFERENCES PLACE (ID),
    CONSTRAINT FK__PLACE_NOTE_PROFILE_ID FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID)
);