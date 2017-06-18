-- add about column for badges
ALTER TABLE  BADGE
ADD ABOUT TEXT;
ALTER TABLE  BADGE
ADD ICON VARCHAR(150);

-- delete unused column
ALTER TABLE  BADGE
DROP COLUMN SCORE;
ALTER TABLE  BADGE
DROP COLUMN ICON_ON;
ALTER TABLE  BADGE
DROP COLUMN ICON_OFF;

-- insert data into badges table
-- Avoir des cours bien notés (plus de 10 notes au dessus de 3 sur un cours) 
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.teacher.name', 'badges.teacher.about', 'teacher');
-- Avoir signalé plus de 10 problèmes
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.superhero.name', 'badges.superhero.about', 'superhero');
-- Avoir validé plus de 10 chapitres sur des cours
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.graduate.name', 'badges.graduate.about', 'graduate');
-- Avoir une équipe et des membres
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.capitaine.name', 'badges.capitaine.about', 'capitaine');
-- Avoir reçu plus de 10 votes sur des participations dans des challenges
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.missionary.name', 'badges.missionary.about', 'missionary');
-- Avoir été affiché dans le leader board
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.star.name', 'badges.star.about', 'star');
-- Avoir rédigé plus de 10 articles dans le journal
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.journalist.name', 'badges.journalist.about', 'journalist');
-- Avoir réalisé un objectif de marche
INSERT INTO BADGE (NAME, ABOUT, ICON) VALUES ('badges.walker.name', 'badges.walker.about', 'walker');