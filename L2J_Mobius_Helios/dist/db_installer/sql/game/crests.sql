DROP TABLE IF EXISTS `crests`;
CREATE TABLE IF NOT EXISTS `crests` (
	`crest_id` INT,
	`data` VARBINARY(2176) NOT NULL,
	`type` TINYINT NOT NULL,
	PRIMARY KEY(`crest_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;