CREATE database storage DEFAULT CHARACTER SET utf8;

CREATE TABLE storage.manufacturers (
id BIGINT(11) NOT NULL AUTO_INCREMENT,
PRIMARY KEY(id),
name VARCHAR(50) NOT NULL,
country VARCHAR(50) NOT NULL,
deleted tinyint NOT NULL DEFAULT 0);

CREATE TABLE storage.drivers (
id BIGINT(11) NOT NULL AUTO_INCREMENT,
PRIMARY KEY (id),
name VARCHAR(50) NOT NULL,
licence VARCHAR(50) NOT NULL,
deleted TINYINT NOT NULL DEFAULT 0);

CREATE TABLE storage.cars (
id BIGINT(11) NOT NULL AUTO_INCREMENT,
PRIMARY KEY (id),
model VARCHAR(50) NOT NULL,
manufacturer_id BIGINT(11) NOT NULL,
deleted TINYINT NOT NULL DEFAULT 0);

ALTER TABLE storage.cars
ADD INDEX cars_manufacturers_idx (manufacturer_id ASC);
ALTER TABLE storage.cars
ADD CONSTRAINT cars_manufacturers_fk
  FOREIGN KEY (manufacturer_id)
  REFERENCES storage.manufacturers (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE TABLE storage.driver_cars (
  driver_id BIGINT(11) NOT NULL,
  car_id BIGINT(11) NOT NULL,
  PRIMARY KEY (driver_id),
  INDEX cars_id_fk_idx (car_id ASC),
  CONSTRAINT drivers_id_fk
    FOREIGN KEY (driver_id)
    REFERENCES storage.drivers (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT cars_id_fk
    FOREIGN KEY (car_id)
    REFERENCES storage.cars (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `storage`.`drivers`
    ADD COLUMN `login` VARCHAR(45) NOT NULL AFTER `deleted`,
ADD COLUMN `password` VARCHAR(45) NOT NULL AFTER `login`;
