-- Modification de la table travel pour ajouter la durée d'utilisation en minute
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 9/01/2017

ALTER TABLE TRAVEL
ADD TOTAL_TIME INTEGER NOT NULL DEFAULT 0;