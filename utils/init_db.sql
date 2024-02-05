DROP DATABASE IF EXISTS "persons";
CREATE DATABASE "persons";

USE "persons";

DROP TABLE IF EXISTS "persons";
CREATE TABLE "public"."persons" (
    "id" serial NOT NULL,
    "first_name" character varying(255) NOT NULL,
    "sur_name" character varying(255) NOT NULL,
    "date_of_birth" date NOT NULL
)