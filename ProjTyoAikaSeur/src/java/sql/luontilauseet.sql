CREATE TABLE KAYTTAJA (
KAYTTAJATUNNUS varchar(255) not null PRIMARY KEY,
SALASANA varchar(255),
NIMI varchar(255),
ROOLI integer NOT NULL
);

CREATE TABLE PROJEKTI (
PROJEKTIN_NIMI varchar(255) PRIMARY KEY,
TYOTUNTIBUDJETTI float(4),
ALKAMISPAIVAMAARA date,
LOPPUMISPAIVAMAARA date
);

CREATE TABLE TYOTEHTAVA (
TYOTEHTAVAN_NIMI varchar(255) PRIMARY KEY,
BUDJETOIDUT_TYOTUNNIT float(4),
TEHDYT_TYOTUNNIT float(4),
SELITYS varchar(500)
);