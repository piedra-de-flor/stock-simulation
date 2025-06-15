CREATE TABLE IF NOT EXISTS `Account`
(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `money` BIGINT NOT NULL,
    `member_id` BIGINT
);

CREATE TABLE IF NOT EXISTS `Member`
(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `nick_name` VARCHAR(255),
    `email` VARCHAR(255),
    `password` VARCHAR(255),
    `account_id` BIGINT,
    CONSTRAINT `fk_member_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `Account` (`id`)
    ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS `Stock`
(
    `code` VARCHAR(255) PRIMARY KEY,
    `price` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `Account_trades`
(
    `account_id` BIGINT NOT NULL,
    `stock_name` VARCHAR(255),
    `stock_code` VARCHAR(255),
    `quantity` INT NOT NULL,
    CONSTRAINT `fk_account_trades_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `Account` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `Account` (`id`, `money`, `member_id`) VALUES (1, 1000000, NULL);
INSERT INTO `Member` (`id`, `nick_name`, `email`, `password`, `account_id`) VALUES (1, 'test', 'test@test.com', 'testpw', 1);
UPDATE `Account` SET `member_id` = 1 WHERE `id` = 1;
INSERT INTO `Stock` (`code`, `price`, `name`) VALUES ('00000', 10000, 'test');