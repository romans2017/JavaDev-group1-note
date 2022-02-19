ALTER TABLE notes
    rename column description to text;

ALTER TABLE notes
    ADD COLUMN type character varying NOT NULL;