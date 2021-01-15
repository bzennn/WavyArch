-- User creation
CREATE USER wavyarch WITH PASSWORD '1234';

-- Tablespace creation
CREATE TABLESPACE wavyarch_tablespace
	OWNER wavyarch
	LOCATION '/wavyarch/database’;
	
-- Database creation
CREATE DATABASE wavyarch
    WITH 
    OWNER = wavyarch
    ENCODING = 'UTF8'
    LC_COLLATE = 'C.UTF-8'
    LC_CTYPE = 'C.UTF-8'
    TABLESPACE = wavyarch_tablespace
    CONNECTION LIMIT = -1;
