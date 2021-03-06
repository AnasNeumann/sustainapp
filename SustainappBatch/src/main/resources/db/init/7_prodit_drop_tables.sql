-- drop all tables before re-install the database
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 01/02/2017
DROP TABLE IF EXISTS VISIT CASCADE;
DROP TABLE IF EXISTS PLACE_NOTE CASCADE;
DROP TABLE IF EXISTS PLACE_PICTURE CASCADE;
DROP TABLE IF EXISTS PLACE CASCADE;
DROP TABLE IF EXISTS CITY CASCADE;
DROP TABLE IF EXISTS ANSWER_CATEGORY CASCADE;
DROP TABLE IF EXISTS RESEARCH CASCADE;
DROP TABLE IF EXISTS PART CASCADE;
DROP TABLE IF EXISTS MISSION CASCADE;
DROP TABLE IF EXISTS NOTIFICATION CASCADE;
DROP TABLE IF EXISTS REPORT CASCADE;
DROP TABLE IF EXISTS AWARD CASCADE;
DROP TABLE IF EXISTS TRAVEL CASCADE;
DROP TABLE IF EXISTS TRANSPORT CASCADE;
DROP TABLE IF EXISTS READ_NEWS CASCADE;
DROP TABLE IF EXISTS NEWS CASCADE;
DROP TABLE IF EXISTS PROFILE_BADGE CASCADE;
DROP TABLE IF EXISTS BADGE CASCADE;
DROP TABLE IF EXISTS CHALLENGE_VOTE CASCADE;
DROP TABLE IF EXISTS PARTICIPATION CASCADE;
DROP TABLE IF EXISTS CHALLENGE CASCADE;
DROP TABLE IF EXISTS CHALLENGE_TYPE CASCADE;
DROP TABLE IF EXISTS TEAM_ROLE CASCADE;
DROP TABLE IF EXISTS TEAM CASCADE;
DROP TABLE IF EXISTS ANSWER CASCADE;
DROP TABLE IF EXISTS QUESTION CASCADE;
DROP TABLE IF EXISTS TOPIC_VALIDATION CASCADE;
DROP TABLE IF EXISTS TOPIC CASCADE;
DROP TABLE IF EXISTS RANK_COURSE CASCADE;
DROP TABLE IF EXISTS COURSE CASCADE;
DROP TABLE IF EXISTS CATEGORY CASCADE;
DROP TABLE IF EXISTS PROFILE CASCADE;
DROP TABLE IF EXISTS USER_ACCOUNT CASCADE;