-- ScriptConnect4_AyD.sql



CREATE TABLE users(
 id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 nickId VARCHAR(25) UNIQUE,
 nameUs VARCHAR(30),
 lastNameUs VARCHAR(20),
 email VARCHAR(30) UNIQUE,
 password VARCHAR(16),
 DNI VARCHAR(15),
 age INT);

-- *********************************************************************************************
-- *********************************************************************************************


CREATE TABLE games(
 id INT(11) NOT NULL AUTO_INCREMENT,
 dateBegin VARCHAR(30),
 dateEnd VARCHAR(30),
 grid_id INT,
 player1_id INT,
 player2_id INT,
 win_id INT,
CONSTRAINT games_pk PRIMARY KEY (id));

-- *********************************************************************************************
-- *********************************************************************************************

CREATE TABLE grids(
 id INT(11) NOT NULL AUTO_INCREMENT,
 X INT,
 Y INT,
CONSTRAINT grids_pk PRIMARY KEY (id));

-- *********************************************************************************************
-- *********************************************************************************************

CREATE TABLE cells(
 id INT(11) NOT NULL AUTO_INCREMENT,
 X INT,
 Y INT,
 state INT,-- ocupadoPorPlayer1 = 1/ocupadoPorPlayer2 = 2 / libre = 0
 grid_id INT,
CONSTRAINT cells_pk PRIMARY KEY (id));

-- *********************************************************************************************
-- *********************************************************************************************

CREATE TABLE ranks(
 id INT(11) NOT NULL AUTO_INCREMENT,
 PG INT,
 PE INT,
 PP INT,
 PJ INT,
 points INT,
 nroRank INT,
 user_id INT,
CONSTRAINT ranks_pk PRIMARY KEY (id));

-- *********************************************************************************************
-- *********************************************************************************************