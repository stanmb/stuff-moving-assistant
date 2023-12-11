CREATE TABLE moving_session
(
    id              BIGSERIAL NOT NULL,
    owner_id        BIGINT    NOT NULL,
    title           TEXT      NOT NULL,
    created_at      DATE      NOT NULL,

    CONSTRAINT moving_session_pk PRIMARY KEY (id)
);

CREATE INDEX ix_owner_id
    ON moving_session (owner_id);

