--liquibase formatted sql

--changeset nvoxland:1
CREATE INDEX studentNameIdx ON student (name);

--changeset nvoxland:2
CREATE INDEX facultyNameColorIdx ON faculty (name, color);