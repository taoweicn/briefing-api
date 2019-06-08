CREATE DATABASE IF NOT EXISTS `briefing` DEFAULT CHARACTER SET utf8mb4;

USE `briefing`;

CREATE TABLE IF NOT EXISTS `user`
(
    id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    student_id VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    type       TINYINT      NOT NULL DEFAULT 1,
    password   CHAR(32)     NOT NULL,
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,

    PRIMARY KEY (id),
    INDEX (student_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `post`
(
    id          INT UNSIGNED NOT NULL AUTO_INCREMENT,
    total_issue INT UNSIGNED NOT NULL,
    issue       INT UNSIGNED NOT NULL,
    content     JSON         NOT NULL,
    creator     INT UNSIGNED NOT NULL,
    modifier    INT UNSIGNED NOT NULL,
    create_time DATETIME     NOT NULL,
    modify_time DATETIME     NOT NULL,
    is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE,

    PRIMARY KEY (id),
    INDEX (total_issue),
    FOREIGN KEY (creator) REFERENCES `user` (id) ON DELETE RESTRICT,
    FOREIGN KEY (modifier) REFERENCES `user` (id) ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
