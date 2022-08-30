DROP
    DATABASE IF EXISTS jackpot_db;
CREATE
    DATABASE IF NOT EXISTS jackpot_db;
USE jackpot_db;


CREATE TABLE `jackpot_db`.`players`
(
    `id`      BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `balance` DECIMAL(19, 4) NOT NULL,
    `name`    VARCHAR(30)    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `jackpot_db`.`casinos`
(
    `id`   BIGINT(50)  NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `jackpot_db`.jackpots
(
    `id`             BIGINT(50) NOT NULL AUTO_INCREMENT,
    `casino_id`       BIGINT(50),
    `trigger_percent` INT(20)    NOT NULL,
    `number_of_levels` INT(20)    NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (casino_Id) REFERENCES casinos (id)
);

CREATE TABLE `jackpot_db`.`levels`
(
    `id`                   BIGINT(50)     NOT NULL AUTO_INCREMENT,
    `total_amount_collected` DECIMAL(19, 4) NOT NULL,
    `percent_of_bet`         DECIMAL(19, 2) NOT NULL,
    `starting_amount`       DECIMAL(19, 4) NOT NULL,
    `minimum_amount_to_be_won` DECIMAL(19, 4) NOT NULL,
    `win_probability`       DECIMAL(19, 4) NOT NULL,
    jackpot_id              BIGINT(50)     NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (jackpot_id) REFERENCES jackpots (id)
);

INSERT INTO `jackpot_db`.`players` (`balance`, `name`)
VALUES ('100000', 'Ivan');
INSERT INTO `jackpot_db`.`players` (`balance`, `name`)
VALUES ('100000', 'Petar');
INSERT INTO `jackpot_db`.`players` (`balance`, `name`)
VALUES ('100000', 'Gosho');

INSERT INTO `jackpot_db`.`casinos` (`name`)
VALUES ('Efbet');
INSERT INTO `jackpot_db`.`casinos` (`name`)
VALUES ('PalmsBet');
INSERT INTO `jackpot_db`.`casinos` (`name`)
VALUES ('WinBet');

INSERT INTO `jackpot_db`.jackpots (`casino_id`, `trigger_percent`, `number_of_levels`)
VALUES ('1', '5', '4');
INSERT INTO `jackpot_db`.jackpots (`casino_id`, `trigger_percent`, `number_of_levels`)
VALUES ('2', '7', '4');
INSERT INTO `jackpot_db`.jackpots (`casino_id`, `trigger_percent`, `number_of_levels`)
VALUES ('3', '4', '4');

INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.1', '100', '200', '50', '1');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.2', '100', '200', '15', '1');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.3', '100', '200', '30', '1');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.4', '100', '200', '5', '1');

INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.1', '100', '200', '50', '2');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.2', '100', '200', '15', '2');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.3', '100', '200', '30', '2');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.4', '100', '200', '5', '2');

INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.1', '100', '200', '50', '3');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.2', '100', '200', '15', '3');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.3', '100', '200', '30', '3');
INSERT INTO `jackpot_db`.`levels` (`total_amount_collected`, `percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`,
                                   `win_probability`, jackpot_id)
VALUES ('100', '0.4', '100', '200', '5', '3');

