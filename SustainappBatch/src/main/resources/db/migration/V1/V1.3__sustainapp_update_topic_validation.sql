-- Modification du mode de validation d'un chapitre d'un cours
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 9/01/2017

ALTER TABLE TOPIC_VALIDATION
DROP COLUMN  COURSE_ID;

ALTER TABLE TOPIC_VALIDATION
ADD TOPIC_ID INTEGER NOT NULL;

ALTER TABLE TOPIC_VALIDATION
ADD CONSTRAINT FK__TOPIC_VALIDATION_TOPIC_ID FOREIGN KEY (TOPIC_ID) REFERENCES TOPIC (ID);