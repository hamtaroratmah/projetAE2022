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
    count_object_got           INTEGER DEFAULT (0),
    precluded                  bool    DEFAULT FALSE
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
    item_condition     VARCHAR(25),
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

INSERT INTO pae.addresses
    (street, building_number, postcode, city)
VALUES ('Rue de Verviers', 47, 4000, 'Liège');

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue du salpêtré', 789, 1040, 'Bruxelles', 'bis');

INSERT INTO pae.addresses
    (street, building_number, postcode, city, unit_number)
VALUES ('Rue des Minières', 45, 4800, 'Verviers', 'Ter');


--Insert demo's users
/*
mdp = Mdpuser.1
mdp2= Rad;293 uniquement pour le dernier membre
*/
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2a$10$AZhMoyNJDcAD7oGnsm.x4.eCJUDNIn6EPk96T/FtZHC8rgL9sDT/W', 'caro',
        'Line', 'Caroline', 1, 'valid');
INSERT INTO pae.members
(password, username, last_name, first_name, address, reason_for_conn_refusal, state)
VALUES ('$2a$10$AZhMoyNJDcAD7oGnsm.x4.eCJUDNIn6EPk96T/FtZHC8rgL9sDT/W', 'achil',
        'Ile', 'Achille', 2, 'L application n est pas encore ouverte à tous', 'denied');
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2a$10$AZhMoyNJDcAD7oGnsm.x4.eCJUDNIn6EPk96T/FtZHC8rgL9sDT/W', 'bazz',
        'Ile', 'Basile', 3, 'valid');
INSERT INTO pae.members
(password, username, last_name, first_name, address, state, isadmin)
VALUES ('$2a$10$KlU8y9chMAVU0DWRtNllL.n.AyKZ.ECHYKy0SKrCXsEZBko2pRagW', 'bri',
        'Lehmann', 'Brigitte', 4, 'valid', true);
--TODO ajouter le bon mot de passe
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2a$10$v29HlojRxRy/b4yqumqy4e9u6OgPIsXL5Gd0YoHdHG.NscL1BfOJy', 'theo',
        'Ile', 'Théophile', 2, 'valid');
INSERT INTO pae.members
(password, username, last_name, first_name, address, state, reason_for_conn_refusal)
VALUES ('$2a$10$pk7yI8xr4WH0.lJBjJlhJ.4g5X0VCuqVpqCoeJamgvSLv0xWBR4/.', 'emi',
        'Ile', 'Emile', 5, 'denied', 'L application n est pas encore ouverte à tous.');
INSERT INTO pae.members
(password, username, last_name, first_name, address, state, reason_for_conn_refusal)
VALUES ('$2a$10$Dec1kcj3zlF/jjCQTVrBQObLnh6HutK27GsBFSiFfYLZaV6APIwkG', 'cora',
        'Line', 'Cora', 6, 'denied', 'L application n est pas encore ouverte à tous.');
INSERT INTO pae.members
    (password, username, last_name, first_name, address, state)
VALUES ('$2a$10$Kyc2bCyeA92EBwr8oaAmF.ZXLkMKvcxCYLniGxJ9m7uNGG7mN6DdO', 'charline',
        'Line', 'Charles', 7, 'pending');

-- Insert demo's items
INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (3, 3, 'Mardi de 17h à 22h', 'Décorations de Noël de couleur rouge',
        'canceled', 'christmas-1869533_640.png');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (3, 3, 'Lundi de 18h à 22h', 'Cadre représentant un chien noir sur un fond noir',
        'published', 'dog-4118585_640.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (4, 8, 'Tous les jours de 15h à 18h', 'Ancien bureau d écolier', 'interestShown',
        'BureauEcolier-7.JPG');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (5, 7, 'Tous les matins avant 11h30',
        'Brouette à deux roue à l avant. Améliore la stabilité et ne fatigue pas le dos',
        'interestShown', 'wheelbarrows-4566619_640.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition)
VALUES (5, 7, 'Tous les matins avant 11h30',
        'Scie sur perche Gardena',
        'published');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (5, 8, 'Tous les matins avant 11h30', 'Table jardin et deux chaises en bois', 'published',
        '3dd3a99b-1a59-4f66-9123-a2f9438aeac6.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (5, 8, 'Lundi de 18h à 20h', 'Table bistro', 'published',
        'table-bistro-a07ea384-d9ee-4f6f-9d4b-e0a8c69c0cac.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (1, 8, 'Samedi en journée', 'Table bistro ancienne de couleur bleue', 'interestShown',
        'table-bistro-carree-bleue-1912de3b-6c28-401b-a3e9-aaf539dbed01.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (5, 4, 'Lundi de 18h à 20h', 'Tableau noir pour enfant', 'assigned',
        'tableau-a00c6153-95aa-4eb1-82d4-cb33b9fcec57.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (5, 3, 'Lundi de 18h30 à 20h', 'Cadre cottage naïf', 'interestShown',
        'cadre-cottage-1178704_640.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (5, 4, 'Lundi de 18h30 à 20h', 'Tasse de couleur claire rose & mauve', 'interestShown',
        'tasse-garden-5037113_640.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (1, 9, 'Lundi de 16h à 17h', 'Pâquerettes dans pots rustiques', 'assigned',
        'pots-daisy-181905_640.jpg');

INSERT INTO pae.items
(id_offering_member, id_type, availabilities, description, item_condition, photo)
VALUES (1, 9, 'Lundi de 16h à 17h', 'Pots en grès pour petites plantes', 'published',
        'pots-plants-6520443_640.jpg');

--Insert demo's offers
INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('21-03-2022', 1);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('25-03-2022', 2);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('25-03-2022', 3);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('28-03-2022', 4);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('28-03-2022', 5);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('29-03-2022', 6);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('29-03-2022', 7);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('30-03-2022', 8);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('14-04-2022', 9);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('21-04-2022', 10);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('21-04-2022', 11);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('21-04-2022', 12);

INSERT INTO pae.offers
    (date_offer, id_item)
VALUES ('21-04-2022', 13);

-- Insert demo's interests
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (3, 5, '16-05-2022');
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (3, 3, '17-05-2022');

INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (4, 4, '09-05-2022');
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (4, 3, '09-05-2022');
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (4, 1, '09-05-2022');

INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (8, 5, '14-05-2022');
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (8, 4, '14-05-2022');

INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (9, 1, '16-05-2022');

INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (10, 4, '09-05-2022');
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (10, 3, '09-05-2022');
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (10, 1, '09-05-2022');

INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (11, 3, '16-05-2022');
INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (11, 1, '16-05-2022');

INSERT INTO pae.interests (id_item, id_member, date_delivery)
values (12, 3, '16-05-2022');

-- Others queries
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

UPDATE pae.interests
SET isrecipient= true
WHERE id_item = 1
  AND id_member = 1;

SELECT *
FROM pae.interests
WHERE id_item = 1;

SELECT *
FROM pae.members
WHERE state = 'denied';
SELECT *
FROm pae.members m,
     pae.addresses a
WHERE m.id_member = 10
  AND a.id_address = m.address;

SELECT *
FROM pae.members;

SELECT *
FROM pae.items;

SELECT *
FROM pae.types;

SELECT *
FROM pae.interests;
UPDATE pae.interests
SET isrecipient= true
WHERE id_item = 5
  AND id_member = 1;

-- INSERT INTO pae.items (id_type, photo, description, availabilities, item_condition,
--                        id_offering_member)
-- VALUES (4, '', '', '', '', 4)
-- RETURNING id_item,id_type,photo,description,availabilities,item_condition,id_offering_member;