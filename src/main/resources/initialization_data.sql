DROP ALL OBJECTS;

CREATE TABLE training_session
(
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    training_type text NOT NULL,
    training_date date NOT NULL,
    training_length_min integer NOT NULL,
    CONSTRAINT training_session_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
    id uuid NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE moves
(
    id uuid NOT NULL,
    name text NOT NULL,
    description text ,
    user_id uuid NOT NULL,
    CONSTRAINT moves_pkey PRIMARY KEY (id)
);