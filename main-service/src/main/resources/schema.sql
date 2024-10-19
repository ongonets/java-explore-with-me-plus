CREATE TABLE IF NOT EXISTS users (
id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
name VARCHAR NOT NULL,
email VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS category (
id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS events (
id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users(id),
category_id BIGINT NOT NULL REFERENCES category(id),
title VARCHAR NOT NULL,
description VARCHAR NOT NULL,
annotation VARCHAR NOT NULL,
event_date TIMESTAMP NOT NULL,
created TIMESTAMP NOT NULL,
published TIMESTAMP,
participant_limit BIGINT NOT NULL,
paid BOOLEAN NOT NULL,
request_moderation BOOLEAN NOT NULL,
state INT8 NOT NULL,
latitude FLOAT NOT NULL,
longitude FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS requests (
id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
user_id BIGINT NOT NULL REFERENCES users(id),
event_id BIGINT NOT NULL REFERENCES events(id),
created TIMESTAMP NOT NULL,
status INT8 NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations (
id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
pinned BOOLEAN NOT NULL,
title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS compilation_events (
compilation_id BIGINT NOT NULL REFERENCES events(id),
event_id BIGINT NOT NULL REFERENCES events(id),
PRIMARY KEY (compilation_id, event_id)
);