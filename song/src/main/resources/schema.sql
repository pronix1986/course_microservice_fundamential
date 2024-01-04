CREATE TABLE IF NOT EXISTS public.song_metadata (
	id serial4 NOT NULL,
	CONSTRAINT song_metadata_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.song_metadata_metadata (
	song_metadata_id int4 NOT NULL,
	metadata varchar(255) NULL,
	metadata_key varchar(255) NOT NULL,
	CONSTRAINT song_metadata_metadata_pkey PRIMARY KEY (song_metadata_id, metadata_key),
	CONSTRAINT fkpsoxv6smtgqqkl2idkc36r1dv FOREIGN KEY (song_metadata_id) REFERENCES public.song_metadata(id)
);