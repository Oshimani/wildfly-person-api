DROP TABLE IF EXISTS "persons";
CREATE TABLE "public"."persons" (
    "name" character varying(255) NOT NULL,
    "surname" character varying(255) NOT NULL,
    "date_of_birth" date,
    CONSTRAINT "persons_pkey" PRIMARY KEY ("name", "surname")
) WITH (oids = false);