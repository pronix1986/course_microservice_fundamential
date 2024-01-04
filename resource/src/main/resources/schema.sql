CREATE TABLE IF NOT EXISTS public.resource (
	id serial4 NOT NULL,
	file oid NULL,
	CONSTRAINT resource_pkey PRIMARY KEY (id)
);