CREATE TABLE kayttaja (
kayttajatunnus varchar(255) PRIMARY KEY,
salasana varchar(255),
nimi varchar(255),
rooli bit NOT NULL
);

CREATE TABLE projekti (
projektin_nimi varchar(255) PRIMARY KEY,
tyotuntibudjetti float(4),
alkamispaivamaara date,
loppumispaivamaara date
);

CREATE TABLE tyotehtava (
tyotehtavan_nimi varchar(255) ,
budjetoidut_tyotunnit float(4),
tehdyt_tyotunnit float(4),
selitys varchar(500)
);