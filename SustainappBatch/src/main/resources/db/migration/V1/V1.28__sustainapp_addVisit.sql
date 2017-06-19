-- Ajout d'une table pour garder en base les visites des gens sur les écolieux
CREATE TABLE VISIT (
    ID SERIAL PRIMARY KEY,
    PLACE_ID INTEGER NOT NULL,
    PROFILE_ID INTEGER NOT NULL,
    TIMESTAMPS TIMESTAMP,
    CONSTRAINT FK__VISIT_PLACE_ID FOREIGN KEY (PLACE_ID) REFERENCES PLACE (ID),
    CONSTRAINT FK__VISIT_PROFILE_ID FOREIGN KEY (PROFILE_ID) REFERENCES PROFILE (ID)
);