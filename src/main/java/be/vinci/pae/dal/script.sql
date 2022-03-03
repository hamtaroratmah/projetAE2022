DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.addresses
(
    id_address      SERIAL PRIMARY KEY,
    street          VARCHAR(25),
    building_number INTEGER,
    postcode        INTEGER,
    commune         VARCHAR(25),
    city            varchar(25),
    unit_number     INTEGER
);

CREATE TABLE pae.members
(
    id_member                  SERIAL PRIMARY KEY,
    password                   VARCHAR(25),
    username                   VARCHAR(25),
    last_name                  VARCHAR(25),
    first_name                 VARCHAR(25),
    address                    INTEGER REFERENCES pae.addresses (id_address),
    call_number                VARCHAR(10),
    isAdmin                    bool    DEFAULT FALSE,
    reason_for_conn_refusal    VARCHAR(300),
    state                      VARCHAR(7),
    count_object_not_collected INTEGER DEFAULT (0),
    count_object_given         INTEGER DEFAULT (0),
    count_object_got           INTEGER DEFAULT (0)
);

CREATE TABLE pae.types
(
    id_type SERIAL PRIMARY KEY,
    type    VARCHAR(25)
);

CREATE TABLE pae.ratings
(
    id_rating           SERIAL PRIMARY KEY,
    rating              INTEGER,
    comment             VARCHAR(300),
    id_recipient_member INTEGER REFERENCES pae.members (id_member)
);

CREATE TABLE pae.items
(
    id_item            SERIAL PRIMARY KEY,
    type               INTEGER REFERENCES pae.types (id_type),
    photo              VARCHAR(100),
    description        VARCHAR(300),
    availabilities     VARCHAR(300),
    item_condition     VARCHAR(10),
    rating             INTEGER REFERENCES pae.ratings (id_rating),
    id_offering_member INTEGER REFERENCES pae.members (id_member)
);



CREATE TABLE pae.offers
(
    id_offer            SERIAL PRIMARY KEY,
    date_offer          DATE,
    id_item             INTEGER REFERENCES pae.items (id_item),
    id_recipient_member INTEGER REFERENCES pae.members (id_member)
);

CREATE TABLE pae.interests
(
    id_interest SERIAL PRIMARY KEY,
    id_item     INTEGER REFERENCES pae.items (id_item),
    id_member   INTEGER REFERENCES pae.members (id_member)
);


INSERT INTO pae.addresses
(street, building_number, postcode, commune, city, unit_number)
VALUES ('laBas', 4, 4781, 'BXl', 'BXL NORD', 2);
INSERT INTO pae.members
(password, last_name, first_name, address, call_number, isadmin, reason_for_conn_refusal,
 state, count_object_not_collected, count_object_given, count_object_got)
VALUES ('123', 'gharroudi', 'souli', 1, '0496873357', DEFAULT, NULL, 'valid', DEFAULT, DEFAULT,
        DEFAULT);
INSERT INTO pae.members
(password, last_name, first_name, address, call_number, isadmin, reason_for_conn_refusal,
 state, count_object_not_collected, count_object_given, count_object_got)
VALUES ('stal', 'bouillon', 'guillaume', 1, '0496847357', DEFAULT, NULL, 'pending', DEFAULT,
        DEFAULT, DEFAULT);


