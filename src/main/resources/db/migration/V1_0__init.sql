CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;

CREATE TYPE device_status AS ENUM('NEW', 'ENABLED', 'DISABLED', 'REMOVED');

CREATE TABLE IF NOT EXISTS account
(
    id         uuid      not null default gen_random_uuid()
        constraint account_pkey primary key,
    email      varchar   not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

CREATE TABLE IF NOT EXISTS station
(
    id          uuid      not null default gen_random_uuid()
        constraint station_pkey primary key,
    external_id varchar   not null,
    name        varchar   not null,
    location    varchar   not null,
    account_id  uuid references account (id),
    created_at  timestamp not null default now(),
    updated_at  timestamp not null default now()
);

CREATE TABLE IF NOT EXISTS device
(
    id          uuid          not null default gen_random_uuid()
        constraint device_pkey primary key,
    external_id varchar       not null,
    name        varchar       not null,
    description varchar       not null,
    actions     text[]        not null,
    status      device_status not null,
    station_id  uuid references station (id),
    created_at  timestamp     not null default now(),
    updated_at  timestamp     not null default now()
);

CREATE TABLE IF NOT EXISTS profile
(
    id         uuid      not null default gen_random_uuid()
        constraint profile_pkey primary key,
    first_name varchar   not null,
    last_name  varchar   not null,
    account_id uuid references account (id),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

CREATE TABLE IF NOT EXISTS assistant
(
    id          uuid      not null default gen_random_uuid()
        constraint assistant_pkey primary key,
    external_id varchar,
    created_at  timestamp not null default now(),
    updated_at  timestamp not null default now()
);

CREATE TABLE IF NOT EXISTS conversation
(
    id                     uuid      not null default gen_random_uuid()
        constraint conversation_pkey primary key,
    conversation_thread_id varchar,
    daily_context          varchar,
    summary                jsonb,
    profile_id             uuid references profile (id),
    assistant_id           uuid references assistant (id),
    created_at             timestamp not null default now(),
    updated_at             timestamp not null default now()
);