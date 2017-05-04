-- delete unused column
ALTER TABLE  NOTIFICATION
DROP COLUMN LINK_TYPE;

-- add a column for share same table between teams and profiles
ALTER TABLE  NOTIFICATION
ADD CREATOR_TYPE INTEGER DEFAULT 0;