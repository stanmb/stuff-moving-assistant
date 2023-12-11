ALTER TABLE moving_box
    ADD CONSTRAINT fk_moving_session_id
        FOREIGN KEY (session_id)
        REFERENCES moving_session (id);