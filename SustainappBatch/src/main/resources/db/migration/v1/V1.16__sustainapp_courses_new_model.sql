-- level min to participate to a course
ALTER TABLE  COURSE
ADD LEVEL_MIN INTEGER DEFAULT 0;

-- the is open or still in redaction
ALTER TABLE  COURSE
ADD OPEN INTEGER DEFAULT 0;

-- topic doesnt need a link or a childlevel
ALTER TABLE  TOPIC
DROP COLUMN LINK;
ALTER TABLE  TOPIC
DROP COLUMN CHILD_LEVEL;
ALTER TABLE  TOPIC
DROP COLUMN PARENT_ID;

-- topic own a list of part
CREATE TABLE PART (
    ID SERIAL PRIMARY KEY,
    TITLE VARCHAR(150),
    CONTENT TEXT,
    DOCUMENT BYTEA NULL,
    PART_TYPE INTEGER DEFAULT 0,
    TIMESTAMPS TIMESTAMP,
    TOPIC_ID INTEGER NOT NULL,
    CONSTRAINT FK_COURSE_TOPIC_ID  FOREIGN KEY (TOPIC_ID) REFERENCES TOPIC (ID)
);