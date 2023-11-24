-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyweb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafyweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyweb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafyweb` ;

-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyweb`.`members` (
  `user_id` VARCHAR(30) NOT NULL,
  `user_name` VARCHAR(45) NULL,
  `user_password` VARCHAR(50) NULL,
  `email_id` VARCHAR(45) NULL,
  `email_domain` VARCHAR(45) NULL,
  `join_date` DATE NULL,
  `refresh_token` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;

USE `ssafyweb` ;

-- -----------------------------------------------------
-- Table `ssafyweb`.`house`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyweb`.`house` (
  `house_id` VARCHAR(20) NOT NULL,
  `build_year` INT NULL DEFAULT NULL,
  `road_name` VARCHAR(45) NULL DEFAULT NULL,
  `road_name_bonbun` VARCHAR(5) NULL DEFAULT NULL,
  `road_name_bubun` VARCHAR(5) NULL DEFAULT NULL,
  `road_name_sigungu_code` VARCHAR(5) NULL DEFAULT NULL,
  `road_name_code` VARCHAR(7) NULL DEFAULT NULL,
  `dong` VARCHAR(40) NULL DEFAULT NULL,
  `bonbun` VARCHAR(4) NULL DEFAULT NULL,
  `bubun` VARCHAR(4) NULL DEFAULT NULL,
  `apartment_name` VARCHAR(45) NULL DEFAULT NULL,
  `jibun` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`house_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyweb`.`housedeal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafyweb`.`housedeal` (
  `housedeal_id` INT NOT NULL AUTO_INCREMENT,
  `house_id` VARCHAR(20) NULL DEFAULT NULL,
  `deal_amount` VARCHAR(40) NULL DEFAULT NULL,
  `area` DOUBLE NULL DEFAULT NULL,
  `deal_year` VARCHAR(4) NULL DEFAULT NULL,
  `deal_month` VARCHAR(2) NULL DEFAULT NULL,
  `deal_day` VARCHAR(6) NULL DEFAULT NULL,
  `floor` VARCHAR(4) NULL DEFAULT NULL,
  PRIMARY KEY (`housedeal_id`),
  INDEX `house_id_idx` (`house_id` ASC) VISIBLE,
  CONSTRAINT `house_id`
    FOREIGN KEY (`house_id`)
    REFERENCES `ssafyweb`.`house` (`house_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
