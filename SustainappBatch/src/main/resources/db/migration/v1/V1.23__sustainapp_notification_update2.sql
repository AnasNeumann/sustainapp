-- delete unused column
ALTER TABLE  NOTIFICATION
DROP CONSTRAINT FK_NOTIFICATION_CREATOR_ID;