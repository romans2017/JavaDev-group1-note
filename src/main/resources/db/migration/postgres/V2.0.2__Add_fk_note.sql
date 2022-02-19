ALTER TABLE notes
    ADD COLUMN user_id UUID NOT NULL;
ALTER TABLE notes
    ADD CONSTRAINT fk_notes_users_id FOREIGN KEY (user_id)
    REFERENCES public.users (id);