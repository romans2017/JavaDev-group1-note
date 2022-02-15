ALTER TABLE notes
    RENAME description TO text;

ALTER TABLE notes
    ADD COLUMN type character varying NOT NULL;