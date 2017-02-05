--creation de la base de donnée --
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 9/01/2017
--unix
SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE datname = 'sustainapp';
--Windows
--SELECT pg_terminate_backend (pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = 'sustainapp';
DROP DATABASE prodit;