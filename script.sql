DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.addresses
(
    id_address      SERIAL PRIMARY KEY,
    street          VARCHAR(25),
    building_number INTEGER,
    postcode        INTEGER,
    city            varchar(25),
    unit_number     varchar(10)
);

CREATE TABLE pae.members
(
    id_member                  SERIAL PRIMARY KEY,
    password                   VARCHAR(60),
    username                   VARCHAR(25),
    last_name                  VARCHAR(25),
    first_name                 VARCHAR(25),
    address                    INTEGER REFERENCES pae.addresses (id_address),
    call_number                VARCHAR(10),
    isAdmin                    bool    DEFAULT FALSE,
    reason_for_conn_refusal    VARCHAR(300),
    state                      VARCHAR(10),
    count_object_not_collected INTEGER DEFAULT (0),
    count_object_given         INTEGER DEFAULT (0),
    count_object_got           INTEGER DEFAULT (0)
);

CREATE TABLE pae.types
(
    id_type SERIAL PRIMARY KEY,
    type    VARCHAR(60)
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
    id_type            INTEGER REFERENCES pae.types (id_type),
    photo              VARCHAR(500),
    description        VARCHAR(300),
    availabilities     VARCHAR(300),
    item_condition     VARCHAR(10),
    rating             INTEGER REFERENCES pae.ratings (id_rating),
    id_offering_member INTEGER REFERENCES pae.members (id_member)
);



CREATE TABLE pae.offers
(
    id_offer   SERIAL PRIMARY KEY,
    date_offer DATE,
    id_item    INTEGER REFERENCES pae.items (id_item)
);

CREATE TABLE pae.interests
(
    id_interest   SERIAL PRIMARY KEY,
    id_item       INTEGER REFERENCES pae.items (id_item),
    id_member     INTEGER REFERENCES pae.members (id_member),
    isRecipient   bool DEFAULT FALSE,
    date_delivery DATE,
    came          bool DEFAULT FALSE
);

-- INSERT FAKE MEMBERS

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue de la loi', 16, 1000, 'Bruxelles', 15);

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'souli',
        'Gharroudi', 'Soulaymane', 1, '0489789546', true, default, 'pending');

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'quentin',
        'Garwig', 'Quentin', 1, '0489789546', true, default, 'valid');

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'stal',
        'Bouillon', 'Guillaume', 1, '0489789546', true, default, 'valid');

INSERT INTO pae.members
(password, username, last_name, first_name, address, call_number, isadmin,
 reason_for_conn_refusal, state)
VALUES ('$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW', 'max',
        'Lecocq', 'Maxime', 1, '0489789546', true, default, 'valid');


--Insert demo's types
INSERT INTO pae.types (type)
VALUES ('Accessoires pour animaux domestiques');
INSERT INTO pae.types (type)
VALUES ('Accessoires pour voiture');
INSERT INTO pae.types (type)
VALUES ('Décoration');
INSERT INTO pae.types (type)
VALUES ('Jouets');
INSERT INTO pae.types (type)
VALUES ('Literie');
INSERT INTO pae.types (type)
VALUES ('Matériel de cuisine');
INSERT INTO pae.types (type)
VALUES ('Matériel de jardinage');
INSERT INTO pae.types (type)
VALUES ('Meuble');
INSERT INTO pae.types (type)
VALUES ('Plantes');
INSERT INTO pae.types (type)
VALUES ('Produits cosmétiques');
INSERT INTO pae.types (type)
VALUES ('Vélo, trottinette');
INSERT INTO pae.types (type)
VALUES ('Vêtements');

--Insert demo's adresses TODO escape simple quote
INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue de l Eglise', 11, 4987, 'Stoumont', 'B1');

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue de Renkin', 7, 4800, 'Verviers', '7');

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue Haute Folie', 6, 4800, 'Verviers', 'A103');

INSERT INTO pae.addresses
    (street, building_number, postcode, city)
VALUES ('Haut-Vinâve', 13, 4845, 'Jalhay');


--Insert demo's users
/*
mdp = Mdpuser.1
mdp2= Rad;293 uniquement pour le dernier membre
*/
INSERT INTO pae.members
(password, username, last_name, first_name, address,
 reason_for_conn_refusal, state)
VALUES ('$2a$10$AZhMoyNJDcAD7oGnsm.x4.eCJUDNIn6EPk96T/FtZHC8rgL9sDT/W', 'caro',
        'Line', 'Caroline', 1, 'Il faudra patienter un jour ou deux.', 'denied');
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2a$10$AZhMoyNJDcAD7oGnsm.x4.eCJUDNIn6EPk96T/FtZHC8rgL9sDT/W', 'achil',
        'Ile', 'Achille', 2, 'valid');
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2a$10$AZhMoyNJDcAD7oGnsm.x4.eCJUDNIn6EPk96T/FtZHC8rgL9sDT/W', 'bazz',
        'Ile', 'Basile', 3, 'valid');
INSERT INTO pae.members
(password, username, last_name, first_name, address, state, isadmin)
VALUES ('$2a$10$9ugYnsv6ogSKOZp4CCO/H.LETYInU4PX9ve63bm4wqZGGR45VO/ia', 'bri',
        'Lehmann', 'Brigitte', 4, 'valid', true);



--Queries for the demo
SELECT id_member, username, isadmin, reason_for_conn_refusal, state
FROM pae.members;

Select it.id_item, it.description, ty.type, it.item_condition, of.date_offer
From pae.items it,
     pae.types ty,
     pae.offers of
Where it.id_type = ty.id_type
  And it.id_item = of.id_item
ORDER BY of.date_offer Desc;

select me.first_name, it.description
from pae.items it,
     pae.members me
order by me.first_name, it.description;

SELECT *
FROM pae.items;
SELECT *
FROM pae.members;


SELECT *
FROM pae.members;

SELECT *
FROM pae.items;

SELECT *
FROM pae.interests
WHERE id_item = 1;

SELECT id_member,
       password,
       username,
       last_name,
       first_name,
       call_number,
       isadmin,
       reason_for_conn_refusal,
       state,
       count_object_not_collected,
       count_object_given,
       count_object_got,
       address
FROM pae.members
WHERE id_member = 3;

UPDATE pae.members
SET password    = '$2a$12$LkYpSJKgVUVn4NcuLddd7eZHm28tRQXTjqVQkTUgLYEP1mlPPRCRW',
    username    = 'quentin659',
    last_name   = 'Garwig',
    first_name  = 'Quentin',
    call_number = 'null'
WHERE id_member = 2;

SELECT * FROM pae.interests WHERE id_item=1 ;

SELECT * FROM pae.members WHERE state= 'denied';
SELECT *
FROm pae.members m,
     pae.addresses a
WHERE m.id_member = 10
  AND a.id_address = m.address;


SELECT * FROM pae.interests;
UPDATE pae.interests SET isrecipient=true WHERE id_item = 5 AND id_member=1;

                                                                INSERT  INTO pae.items (id_type,photo, description, availabilities, item_condition,id_offering_member) VALUES(4,'','','','',4) RETURNING id_item,id_type,photo,description,availabilities,item_condition,id_offering_member;

