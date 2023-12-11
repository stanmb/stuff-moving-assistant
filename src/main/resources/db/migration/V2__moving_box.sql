CREATE TABLE moving_box
(
    id              BIGSERIAL NOT NULL,
    session_id      BIGINT    NOT NULL,
    title           TEXT      NOT NULL,
    img_url         TEXT      NULL,
    qr_url          TEXT      NULL,
    archived        BOOL      NOT NULL,
    created_at      DATE      NOT NULL,
    updated_at      DATE      NOT NULL,
    extras          JSONB     NOT NULL DEFAULT '{}'::jsonb,

    CONSTRAINT moving_box_pk PRIMARY KEY (id)
);

CREATE INDEX ix_session_od
    ON moving_box (session_id);