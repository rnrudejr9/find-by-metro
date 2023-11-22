-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyweb` DEFAULT CHARACTER SET utf8 ;
USE `ssafyweb` ;

-- -----------------------------------------------------
-- Table `ssafyweb`.`house`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyweb`.`house` (
  `house_id` VARCHAR(20) NOT NULL,
  `build_year` INT NULL,
  `road_name` VARCHAR(45) NULL,
  `road_name_bonbun` VARCHAR(5) NULL,
  `road_name_bubun` VARCHAR(5) NULL,
  `road_name_sigungu_code` VARCHAR(5) NULL,
  `road_name_code` VARCHAR(7) NULL,
  `dong` VARCHAR(40) NULL,
  `bonbun` VARCHAR(4) NULL,
  `bubun` VARCHAR(4) NULL,
  `apartment_name` VARCHAR(45) NULL,
  `jibun` VARCHAR(10) NULL,
  PRIMARY KEY (`house_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafyweb`.`housedeal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyweb`.`housedeal` (
  `housedeal_id` INT NOT NULL AUTO_INCREMENT,
  `house_id` VARCHAR(20) NULL,
  `deal_amount` VARCHAR(40) NULL,
  `area` DOUBLE NULL,
  `deal_year` VARCHAR(4) NULL,
  `deal_month` VARCHAR(2) NULL,
  `deal_day` VARCHAR(6) NULL,
  `floor` VARCHAR(4) NULL,
  PRIMARY KEY (`housedeal_id`),
  INDEX `house_id_idx` (`house_id` ASC) VISIBLE,
  CONSTRAINT `house_id`
    FOREIGN KEY (`house_id`)
    REFERENCES `ssafyweb`.`house` (`house_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;