-- Drop schema if exists
DROP SCHEMA IF EXISTS music_library CASCADE;

-- Drop cookie table if exists
DROP TABLE IF EXISTS public.persistent_logins;

-- Schema creation
CREATE SCHEMA music_library;


-- Audios, Genres, Albums creation
CREATE TABLE music_library.Audios (
	audio_id bigserial NOT NULL,
	name character varying(60) NOT NULL,
	file_path character varying(500) NOT NULL,
	duration int NOT NULL,
	creation_date date NULL,
	genre_id int NULL,
	album_id int NULL,
	CONSTRAINT pk_audios PRIMARY KEY (audio_id),
	CONSTRAINT ak_audios_name UNIQUE (name),
	CONSTRAINT ak_audios_file_path UNIQUE (file_path)
);

CREATE TABLE music_library.Genres (
	genre_id serial NOT NULL,
	name character varying(60) NOT NULL,
	CONSTRAINT pk_genres PRIMARY KEY (genre_id),
	CONSTRAINT ak_genres_name UNIQUE (name)
);
	
CREATE TABLE music_library.Albums (
	album_id serial NOT NULL,
	name character varying(200) NOT NULL,
	image_path character varying(200),
	creation_date date NULL,
	CONSTRAINT pk_albums PRIMARY KEY (album_id),
	CONSTRAINT ak_albums_name UNIQUE (name),
	CONSTRAINT ak_albums_image_path UNIQUE (image_path)
);

ALTER TABLE music_library.Audios 
	ADD CONSTRAINT fk_audios_genres FOREIGN KEY (genre_id) REFERENCES music_library.Genres (genre_id) ON DELETE NO ACTION,
	ADD CONSTRAINT fk_audios_albums FOREIGN KEY (album_id) REFERENCES music_library.Albums (album_id) ON DELETE NO ACTION;


-- Accounts, AccountRoles creation
CREATE TABLE music_library.Accounts (
	account_id bigserial NOT NULL,
	login character varying(20) NOT NULL,
	password_hash character varying(100) NOT NULL,
	image_path character varying(200) NULL,
	role_id int NOT NULL,
	CONSTRAINT pk_accounts PRIMARY KEY (account_id),
	CONSTRAINT ak_accounts_login UNIQUE (login),
	CONSTRAINT ak_accounts_password_hash UNIQUE (password_hash)
);

CREATE TABLE music_library.AccountRoles (
	role_id serial NOT NULL,
	name character varying(20) NOT NULL,
	CONSTRAINT pk_account_roles PRIMARY KEY (role_id),
	CONSTRAINT ak_account_roles_name UNIQUE (name)
);

ALTER TABLE music_library.Accounts
	ADD CONSTRAINT fk_accounts_roles FOREIGN KEY (role_id) REFERENCES music_library.AccountRoles (role_id);


-- AudioTags, Tags creation
CREATE TABLE music_library.AudioTags (
	audio_id bigint NOT NULL,
	tag_id bigint NOT NULL,
	CONSTRAINT pk_audio_tags PRIMARY KEY (audio_id, tag_id)
);

CREATE TABLE music_library.Tags (
	tag_id bigserial NOT NULL,
	name character varying(20) NOT NULL,
	CONSTRAINT pk_tags PRIMARY KEY (tag_id),
	CONSTRAINT ak_tags_name UNIQUE (name)
);

ALTER TABLE music_library.AudioTags
	ADD CONSTRAINT fk_audio_tags_audio FOREIGN KEY (audio_id) REFERENCES music_library.Audios (audio_id) ON DELETE CASCADE,
	ADD CONSTRAINT fk_audio_tags_tag FOREIGN KEY (tag_id) REFERENCES music_library.Tags (tag_id) ON DELETE CASCADE;


-- AudioMakers, Performers, Authors, AuthorRoles creation
CREATE TABLE music_library.AudioMakers (
	audio_maker_id bigserial NOT NULL,
	name character varying(60) NOT NULL,
	image_path character varying(200) NULL,
	creation_date date NULL,
	description character varying(200) NULL,
	CONSTRAINT pk_audio_makers PRIMARY KEY (audio_maker_id),
	CONSTRAINT ak_audio_makers_name UNIQUE (name),
	CONSTRAINT ak_audio_makers_image_path UNIQUE (image_path)
);

CREATE TABLE music_library.Performers (
	audio_id bigint NOT NULL,
	audio_maker_id bigint NOT NULL,
	CONSTRAINT pk_performers PRIMARY KEY (audio_id, audio_maker_id)
);

CREATE TABLE music_library.Authors (
	audio_id bigint NOT NULL,
	audio_maker_id bigint NOT NULL,
	role_id int NOT NULL,
	CONSTRAINT pk_authors PRIMARY KEY (audio_id, audio_maker_id)
);

CREATE TABLE music_library.AuthorRoles (
	role_id serial NOT NULL,
	name character varying(20) NOT NULL,
	CONSTRAINT pk_author_roles PRIMARY KEY (role_id),
	CONSTRAINT ak_author_roles_name UNIQUE (name)
);

ALTER TABLE music_library.Performers
	ADD CONSTRAINT fk_performers_audios FOREIGN KEY (audio_id) REFERENCES music_library.Audios (audio_id) ON DELETE CASCADE,
	ADD CONSTRAINT fk_performers_audio_makers FOREIGN KEY (audio_maker_id) REFERENCES music_library.AudioMakers (audio_maker_id) ON DELETE CASCADE;

ALTER TABLE music_library.Authors
	ADD CONSTRAINT fk_authors_audios FOREIGN KEY (audio_id) REFERENCES music_library.Audios (audio_id) ON DELETE CASCADE,
	ADD CONSTRAINT fk_authors_audio_makers FOREIGN KEY (audio_maker_id) REFERENCES music_library.AudioMakers (audio_maker_id) ON DELETE CASCADE,
	ADD CONSTRAINT fk_authors_role FOREIGN KEY (role_id) REFERENCES music_library.AuthorRoles (role_id) ON DELETE NO ACTION;
	
	
-- AccountAudios creation
CREATE TABLE music_library.AccountAudios (
	account_id bigint NOT NULL,
	audio_id bigint NOT NULL,
	added_date timestamp NOT NULL DEFAULT now(),
	CONSTRAINT pk_account_audios PRIMARY KEY (account_id, audio_id)
);

ALTER TABLE music_library.AccountAudios
	ADD CONSTRAINT fk_account_audio_account FOREIGN KEY (account_id) REFERENCES music_library.Accounts (account_id) ON DELETE CASCADE,
	ADD CONSTRAINT fk_account_audio_audio FOREIGN KEY (audio_id) REFERENCES music_library.Audios (audio_id) ON DELETE CASCADE;


-- AccountPlaylists, PlaylistAudios creation
CREATE TABLE music_library.AccountPlaylists (
	playlist_id bigserial NOT NULL,
	name character varying(100) NOT NULL,
	image_path character varying(200) NULL,
	added_date timestamp NOT NULL DEFAULT now(),
	account_id bigint NOT NULL, -- add not null
	CONSTRAINT pk_account_playlists PRIMARY KEY (playlist_id),
	CONSTRAINT ak_account_playlists UNIQUE (name, account_id),
	CONSTRAINT ak_account_playlists_image_path UNIQUE (image_path)
);

CREATE TABLE music_library.PlaylistAudios (
	playlist_id bigint NOT NULL,
	audio_id bigint NOT NULL,
	added_date timestamp NOT NULL DEFAULT now(),
	CONSTRAINT pk_playlist_audios PRIMARY KEY (playlist_id, audio_id)
);

ALTER TABLE music_library.AccountPlaylists
	ADD CONSTRAINT fk_account_playlists_account FOREIGN KEY (account_id) REFERENCES music_library.Accounts (account_id) ON DELETE CASCADE;
	
ALTER TABLE music_library.PlaylistAudios 
	ADD CONSTRAINT fk_playlist_audios_playlist FOREIGN KEY (playlist_id) REFERENCES music_library.AccountPlaylists (playlist_id) ON DELETE CASCADE,
	ADD CONSTRAINT fk_playlist_audios_audio FOREIGN KEY (audio_id) REFERENCES music_library.Audios (audio_id) ON DELETE CASCADE;

INSERT INTO music_library.accountroles (name) VALUES ('admin'), ('user');
INSERT INTO music_library.authorroles (name) VALUES ('Author');

CREATE TABLE public.persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    CONSTRAINT pk_persistent_logins PRIMARY KEY (series)
);

CREATE EXTENSION pg_trgm;

CREATE INDEX ix_audios_name ON music_library.audios USING HASH (name);
CREATE INDEX ix_accounts_login ON music_library.accounts USING HASH (login);
CREATE INDEX ix_account_roles_name ON music_library.accountroles USING HASH (name);
CREATE INDEX ix_genres_name ON music_library.genres USING HASH (name);
CREATE INDEX ix_albums_name ON music_library.albums USING HASH (name);
CREATE INDEX ix_audio_makers_name ON music_library.audiomakers USING HASH (name);
CREATE INDEX ix_account_playlists_name ON music_library.accountplaylists USING HASH (name);
CREATE INDEX ix_tags_name ON music_library.tags USING HASH (name);
CREATE INDEX ix_author_roles_name ON music_library.authorroles USING HASH (name);

CREATE INDEX trgm_ix_audios_name ON music_library.audios USING GIST (name gist_trgm_ops);
CREATE INDEX trgm_ix_accounts_login ON music_library.accounts USING GIST (login gist_trgm_ops);
CREATE INDEX trgm_ix_account_roles_name ON music_library.accountroles USING GIST (name gist_trgm_ops);
CREATE INDEX trgm_ix_genres_name ON music_library.genres USING GIST (name gist_trgm_ops);
CREATE INDEX trgm_ix_albums_name ON music_library.albums USING GIST (name gist_trgm_ops);
CREATE INDEX trgm_ix_audio_makers_name ON music_library.audiomakers USING GIST (name gist_trgm_ops);
CREATE INDEX trgm_ix_account_playlists_name ON music_library.accountplaylists USING GIST (name gist_trgm_ops);
CREATE INDEX trgm_ix_tags_name ON music_library.tags USING GIST (name gist_trgm_ops);
CREATE INDEX trgm_ix_author_roles_name ON music_library.authorroles USING GIST (name gist_trgm_ops);

SELECT show_limit(), set_limit(0.1);

CREATE INDEX ix_audios_creation_date ON music_library.audios USING BTREE (creation_date);
CREATE INDEX ix_albums_creation_date ON music_library.albums USING BTREE (creation_date);
CREATE INDEX ix_audio_makers_creation_date ON music_library.audiomakers USING BTREE (creation_date);
CREATE INDEX ix_account_playlists_added_date ON music_library.accountplaylists USING BTREE (added_date);
CREATE INDEX ix_playlist_audios_added_date ON music_library.playlistaudios USING BTREE (added_date);
CREATE INDEX ix_account_audios_added_date ON music_library.accountaudios USING BTREE (added_date);

-- PlaylistAudios insert-update trigger
CREATE FUNCTION tg_playlistaudios_insert_update() RETURNS trigger AS $tg_playlistaudios_insert_update$
	BEGIN
		IF NOT EXISTS (SELECT audio_id FROM music_library.audios WHERE audio_id = NEW.audio_id) THEN
			RAISE EXCEPTION 'audio with this id not exists';
		END IF;
		
		IF NOT EXISTS (SELECT playlist_id FROM music_library.accountplaylists WHERE playlist_id = NEW.playlist_id) THEN
			RAISE EXCEPTION 'playlist with this id not exists';
		END IF;
		
		RETURN NEW;
	END;
$tg_playlistaudios_insert_update$ LANGUAGE plpgsql;

CREATE TRIGGER tg_playlistaudios_insert_update BEFORE INSERT OR UPDATE ON music_library.playlistaudios
	FOR EACH ROW EXECUTE FUNCTION tg_playlistaudios_insert_update();
	
-- AccountAudios insert-update trigger
CREATE FUNCTION tg_accountaudios_insert_update() RETURNS trigger AS $tg_accountaudios_insert_update$
	BEGIN
		IF NOT EXISTS (SELECT audio_id FROM music_library.audios WHERE audio_id = NEW.audio_id) THEN
			RAISE EXCEPTION 'audio with this id not exists';
		END IF;
		
		IF NOT EXISTS (SELECT account_id FROM music_library.accounts WHERE account_id = NEW.account_id) THEN
			RAISE EXCEPTION 'account with this id not exists';
		END IF;
		
		RETURN NEW;
	END;
$tg_accountaudios_insert_update$ LANGUAGE plpgsql;

CREATE TRIGGER tg_accountaudios_insert_update BEFORE INSERT OR UPDATE ON music_library.accountaudios
	FOR EACH ROW EXECUTE FUNCTION tg_accountaudios_insert_update();
	
-- AudioTags insert-update trigger
CREATE FUNCTION tg_audiotags_insert_update() RETURNS trigger AS $tg_audiotags_insert_update$
	BEGIN
		IF NOT EXISTS (SELECT audio_id FROM music_library.audios WHERE audio_id = NEW.audio_id) THEN
			RAISE EXCEPTION 'audio with this id not exists';
		END IF;
		
		IF NOT EXISTS (SELECT tag_id FROM music_library.tags WHERE tag_id = NEW.tag_id) THEN
			RAISE EXCEPTION 'tag with this id not exists';
		END IF;
		
		RETURN NEW;
	END;
$tg_audiotags_insert_update$ LANGUAGE plpgsql;

CREATE TRIGGER tg_audiotags_insert_update BEFORE INSERT OR UPDATE ON music_library.audiotags
	FOR EACH ROW EXECUTE FUNCTION tg_audiotags_insert_update();
	
-- Performers insert-update trigger
CREATE FUNCTION tg_performers_insert_update() RETURNS trigger AS $tg_performers_insert_update$
	BEGIN
		IF NOT EXISTS (SELECT audio_id FROM music_library.audios WHERE audio_id = NEW.audio_id) THEN
			RAISE EXCEPTION 'audio with this id not exists';
		END IF;
		
		IF NOT EXISTS (SELECT audio_maker_id FROM music_library.audiomakers WHERE audio_maker_id = NEW.audio_maker_id) THEN
			RAISE EXCEPTION 'audio maker with this id not exists';
		END IF;
		
		RETURN NEW;
	END;
$tg_performers_insert_update$ LANGUAGE plpgsql;

CREATE TRIGGER tg_performers_insert_update BEFORE INSERT OR UPDATE ON music_library.performers
	FOR EACH ROW EXECUTE FUNCTION tg_performers_insert_update();
	
-- Authors insert-update trigger
CREATE FUNCTION tg_authors_insert_update() RETURNS trigger AS $tg_authors_insert_update$
	BEGIN
		IF NOT EXISTS (SELECT audio_id FROM music_library.audios WHERE audio_id = NEW.audio_id) THEN
			RAISE EXCEPTION 'audio with this id not exists';
		END IF;
		
		IF NOT EXISTS (SELECT audio_maker_id FROM music_library.audiomakers WHERE audio_maker_id = NEW.audio_maker_id) THEN
			RAISE EXCEPTION 'audio maker with this id not exists';
		END IF;
		
		RETURN NEW;
	END;
$tg_authors_insert_update$ LANGUAGE plpgsql;

CREATE TRIGGER tg_authors_insert_update BEFORE INSERT OR UPDATE ON music_library.authors
	FOR EACH ROW EXECUTE FUNCTION tg_authors_insert_update();
	
-- Get audio tags
CREATE FUNCTION music_library.getTagsByAudio(audio character varying(60))
RETURNS setof music_library.tags AS 
$$
SELECT DISTINCT tags.*
FROM music_library.audios as audios
INNER JOIN music_library.audiotags as audiotags
	ON audios.audio_id = audiotags.audio_id
INNER JOIN music_library.tags as tags
	ON audiotags.tag_id = tags.tag_id
WHERE audios.name = audio
$$ LANGUAGE sql;

--DROP FUNCTION music_library.getTagsByAudio;

-- Get audio authors
CREATE FUNCTION music_library.getAuthorsByAudio(audio character varying(60))
RETURNS setof music_library.audiomakers AS 
$$
SELECT DISTINCT audiomakers.*
FROM music_library.audios as audios
INNER JOIN music_library.authors as authors
	ON audios.audio_id = authors.audio_id
INNER JOIN music_library.audiomakers as audiomakers
	ON authors.audio_maker_id = audiomakers.audio_maker_id
WHERE audios.name = audio
$$ LANGUAGE sql;

--DROP FUNCTION music_library.getAuthorsByAudio;

-- Get audio performers
CREATE FUNCTION music_library.getPerformersByAudio(audio character varying(60))
RETURNS setof music_library.audiomakers AS 
$$
SELECT DISTINCT audiomakers.*
FROM music_library.audios as audios
INNER JOIN music_library.performers as performers
	ON audios.audio_id = performers.audio_id
INNER JOIN music_library.audiomakers as audiomakers
	ON performers.audio_maker_id = audiomakers.audio_maker_id
WHERE audios.name = audio
$$ LANGUAGE sql;

--DROP FUNCTION music_library.getPerformersByAudio;

-- Get similarity by performers
CREATE FUNCTION music_library.getSimilarityByPerformers(audio1 character varying(60), audio2 character varying(60))
RETURNS bigint AS 
$$
SELECT COUNT(*)
FROM (
	SELECT * FROM  music_library.getPerformersByAudio(audio1)
	INTERSECT
	SELECT * FROM  music_library.getPerformersByAudio(audio2)
) AS its
$$ LANGUAGE sql;

--DROP FUNCTION music_library.getSimilarityByPerformers;

-- Get similarity by authors
CREATE FUNCTION music_library.getSimilarityByAuthors(audio1 character varying(60), audio2 character varying(60))
RETURNS bigint AS 
$$
SELECT COUNT(*)
FROM (
	SELECT * FROM  music_library.getAuthorsByAudio(audio1)
	INTERSECT
	SELECT * FROM  music_library.getAuthorsByAudio(audio2)
) AS its
$$ LANGUAGE sql;

--DROP FUNCTION music_library.getSimilarityByAuthors;

-- Get similarity by album
CREATE FUNCTION music_library.getSimilarityByAlbum(audio1 character varying(60), audio2 character varying(60))
RETURNS integer AS 
$$
DECLARE
	album1 character varying(200);
	album2 character varying(200);
BEGIN
	SELECT albums.name INTO album1
	FROM music_library.albums as albums
	INNER JOIN music_library.audios as audios
	ON audios.album_id = albums.album_id
	WHERE audios.name = audio1;
	
	SELECT albums.name INTO album2
	FROM music_library.albums as albums
	INNER JOIN music_library.audios as audios
	ON audios.album_id = albums.album_id
	WHERE audios.name = audio2;

	IF (album1 = album2) 
	THEN RETURN 1;
	ELSE RETURN 0;
	END IF;	
END;
$$ LANGUAGE plpgsql;

--DROP FUNCTION music_library.getSimilarityByAlbum;

-- Get similarity by tags
CREATE FUNCTION music_library.getSimilarityByTags(audio1 character varying(60), audio2 character varying(60))
RETURNS bigint AS 
$$
SELECT COUNT(*)
FROM (
	SELECT * FROM  music_library.getTagsByAudio(audio1)
	INTERSECT
	SELECT * FROM  music_library.getTagsByAudio(audio2)
) AS its
$$ LANGUAGE sql;

--DROP FUNCTION music_library.getSimilarityByTags;

-- Get similarity by duration
CREATE FUNCTION music_library.getSimilarityByDuration(audio1 character varying(60), audio2 character varying(60))
RETURNS integer AS 
$$
DECLARE
	duration1 integer;
	duration2 integer;
BEGIN
	SELECT duration INTO duration1 FROM music_library.audios WHERE name = audio1;
	SELECT duration INTO duration2 FROM music_library.audios WHERE name = audio2;

	IF (duration2 >= duration1 - 60 AND duration2 <= duration1 + 60) 
	THEN RETURN 1;
	ELSE RETURN 0;
	END IF;	
END;
$$ LANGUAGE plpgsql;

--DROP FUNCTION music_library.getSimilarityByDuration;

-- Get similarity by genre
CREATE FUNCTION music_library.getSimilarityByGenre(audio1 character varying(60), audio2 character varying(60))
RETURNS integer AS 
$$
DECLARE
	genre1 character varying(60);
	genre2 character varying(60);
BEGIN
	SELECT genres.name INTO genre1
	FROM music_library.genres as genres
	INNER JOIN music_library.audios as audios
	ON audios.genre_id = genres.genre_id
	WHERE audios.name = audio1;
	
	SELECT genres.name INTO genre2
	FROM music_library.genres as genres
	INNER JOIN music_library.audios as audios
	ON audios.genre_id = genres.genre_id
	WHERE audios.name = audio2;

	IF (genre1 = genre2) 
	THEN RETURN 1;
	ELSE RETURN 0;
	END IF;	
END;
$$ LANGUAGE plpgsql;

--DROP FUNCTION music_library.getSimilarityByGenre;

-- Build recommendations by audio
CREATE FUNCTION music_library.buildRecommendationsByAudio(audio character varying(60), maxSize integer)
RETURNS setof music_library.audios AS 
$$
SELECT audios.*
FROM 
	music_library.audios as audios
WHERE 
	name != audio AND (
	music_library.getSimilarityByTags(audio, audios.name) >= 1 OR
	music_library.getSimilarityByGenre(audio, audios.name) >= 1 OR
	music_library.getSimilarityByAlbum(audio, audios.name) >= 1 OR
	music_library.getSimilarityByPerformers(audio, audios.name) >= 1 OR
	music_library.getSimilarityByAuthors(audio, audios.name) >= 1)
ORDER BY
	music_library.getSimilarityByTags(audio, audios.name) DESC NULLS LAST,
	music_library.getSimilarityByGenre(audio, audios.name) DESC NULLS LAST,
	music_library.getSimilarityByAlbum(audio, audios.name) DESC NULLS LAST,
	music_library.getSimilarityByPerformers(audio, audios.name) DESC NULLS LAST,
	music_library.getSimilarityByAuthors(audio, audios.name) DESC NULLS LAST,
	music_library.getSimilarityByDuration(audio, audios.name) DESC NULLS LAST,
	similarity(audio, audios.name) DESC NULLS LAST
LIMIT maxSize
$$ LANGUAGE sql;

--DROP FUNCTION music_library.buildRecommendationsByAudio;