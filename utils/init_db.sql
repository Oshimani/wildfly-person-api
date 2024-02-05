DROP TABLE IF EXISTS "persons";
CREATE TABLE "public"."persons" (
    "first_name" character varying(255) NOT NULL,
    "sur_name" character varying(255) NOT NULL,
    "date_of_birth" date,
    CONSTRAINT "persons_pkey" PRIMARY KEY ("first_name", "sur_name")
) WITH (oids = false);