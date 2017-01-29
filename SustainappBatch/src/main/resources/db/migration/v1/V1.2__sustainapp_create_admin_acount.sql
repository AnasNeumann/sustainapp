-- Insertion par defaut d'un administrateur
-- created by Anas Neumann <anas.neumann.isamm@gmail.com>
-- since 9/01/2017
TRUNCATE PROFILE CASCADE;
INSERT INTO PROFILE (
	    LAST_NAME,
	    FIRST_NAME,
	    MAIL,
	    PASSWORD,
	    BORN_DATE,
	    COVER,
	    AVATAR,
	    IS_ADMIN,
	    TIMESTAMPS
    ) VALUES (
	    'sustainapp',
	    'admin',
	    'admin@sustainapp.ca',
	    'root',
	    null,
	    null,
	    null,
	    0,
	    null
);