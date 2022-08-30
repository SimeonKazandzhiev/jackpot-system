CREATE TABLE `players`
(
    `id`      BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `balance` DECIMAL(19, 4) NOT NULL,
    `name`    VARCHAR(50)    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `casinos`
(
    `id`   BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `jackpots`
(
    `id`             BIGINT(20) NOT NULL AUTO_INCREMENT,
    `casino_id`       BIGINT(20),
    `trigger_percent` INT(20)    NOT NULL,
    `number_of_levels` INT(20)    NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (casino_Id) REFERENCES casinos (id)
);

CREATE TABLE `levels`
(
    `id`                   BIGINT(20)     NOT NULL AUTO_INCREMENT,
    `total_amount_collected` DECIMAL(19, 4) NOT NULL,
    `percent_of_bet`         DECIMAL(19, 2) NOT NULL,
    `starting_amount`       DECIMAL(19, 4) NOT NULL,
    `minimum_amount_to_be_won` DECIMAL(19, 4) NOT NULL,
    `win_probability`       DECIMAL(19, 4) NOT NULL,
    jackpot_id              BIGINT(20) ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (jackpot_id) REFERENCES jackpots (id)
);