-- change challengeType icon format
ALTER TABLE CHALLENGE_TYPE
DROP COLUMN  ICON;

ALTER TABLE CHALLENGE_TYPE
ADD ICON VARCHAR(200) DEFAULT NULL;

-- insert all challengeType
INSERT INTO CHALLENGE_TYPE (NAME,ICON) VALUES ('challengetype.building','building');
INSERT INTO CHALLENGE_TYPE (NAME,ICON) VALUES ('challengetype.design','design');
INSERT INTO CHALLENGE_TYPE (NAME,ICON) VALUES ('challengetype.energy','energy');
INSERT INTO CHALLENGE_TYPE (NAME,ICON) VALUES ('challengetype.gastronomy','gastronomy');
INSERT INTO CHALLENGE_TYPE (NAME,ICON) VALUES ('challengetype.manage','manage');
INSERT INTO CHALLENGE_TYPE (NAME,ICON) VALUES ('challengetype.recycling','recycling');