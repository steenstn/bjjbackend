DROP ALL OBJECTS;

CREATE TABLE training_session
(
    id uuid NOT NULL,
    training_type text  NOT NULL,
    training_date date NOT NULL,
    training_length_min integer NOT NULL,
    CONSTRAINT training_session_pkey PRIMARY KEY (id)
);

