DROP TABLE IF EXISTS  room;
CREATE TABLE room
(
    id bigserial,
    name character varying(30) NOT NULL,
    country character varying(30) NOT NULL,
    bulb_on bool NOT NULL
);