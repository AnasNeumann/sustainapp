--creation de la base de donn�e --
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 01/02/2017
--unix
SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE datname = 'sustainapp_test';
--Windows
--SELECT pg_terminate_backend (pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = 'sustainapp_test';
DROP DATABASE prodit;