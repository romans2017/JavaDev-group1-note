ALTER TABLE notes
    ALTER COLUMN description RENAME TO text;

ALTER TABLE notes
    ADD COLUMN type character varying NOT NULL;