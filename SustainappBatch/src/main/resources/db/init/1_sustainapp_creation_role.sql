--creation de role --
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 01/02/2017

CREATE ROLE sustainapp_admin LOGIN
 PASSWORD 'sustainapp'
 NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;