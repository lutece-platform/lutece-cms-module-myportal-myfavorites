
--
-- Structure for table myportal_myfavorites_myfavorites
--

DROP TABLE IF EXISTS myportal_myfavorites_myfavorites;
CREATE TABLE myportal_myfavorites_myfavorites (
id_my_favorites int(6) NOT NULL,
url varchar(255) default '' NOT NULL,
id_icon int(11) default '0',
label varchar(50) default '' NOT NULL,
user_id varchar(50) default '',

PRIMARY KEY (id_my_favorites)
);
