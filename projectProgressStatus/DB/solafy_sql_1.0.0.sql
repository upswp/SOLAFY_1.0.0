-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema solafy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema solafy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `solafy` DEFAULT CHARACTER SET utf8 ;
USE `solafy` ;

-- -----------------------------------------------------
-- Table `solafy`.`Problem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`Problem` (
  `problemNo` INT NOT NULL AUTO_INCREMENT,
  `multipleChoice` TEXT NULL,
  `title` VARCHAR(100) NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `contents` TEXT NOT NULL,
  `starScore` DOUBLE NOT NULL DEFAULT 0,
  `categoryLarge` VARCHAR(45) NOT NULL,
  `categoryMedium` VARCHAR(45) NOT NULL,
  `categorySmall` VARCHAR(45) NOT NULL,
  `type` INT NOT NULL,
  PRIMARY KEY (`problemNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`Group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`Group` (
  `groupNo` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `type` TINYINT NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`groupNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`QuestionBoard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`QuestionBoard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `problemNo` INT NOT NULL,
  `point` INT NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` TINYINT NULL,
  `groupNo` INT NULL,
  PRIMARY KEY (`articleNo`),
  INDEX `questionBoard_problemNo_problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  INDEX `QuestionBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `questionBoard_problemNo_problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`Problem` (`problemNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `QuestionBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`Group` (`groupNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`AnswerByQuestion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`AnswerByQuestion` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `questionNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  INDEX `Answer_questionNo_question_articleNo_idx` (`questionNo` ASC) VISIBLE,
  CONSTRAINT `Answer_questionNo_question_articleNo`
    FOREIGN KEY (`questionNo`)
    REFERENCES `solafy`.`QuestionBoard` (`articleNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`AnswerModifyBoard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`AnswerModifyBoard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(50) NOT NULL,
  `problemNo` INT NOT NULL,
  `uid_submitter` VARCHAR(50) NOT NULL,
  `contents` TEXT NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  INDEX `answerModifyBoard_problemNo_problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  CONSTRAINT `answerModifyBoard_problemNo_problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`Problem` (`problemNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`FreeBoard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`FreeBoard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `likeCount` INT NOT NULL DEFAULT 0,
  `isNotice` TINYINT NOT NULL DEFAULT 0,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` TINYINT NOT NULL,
  `groupNo` INT NULL,
  `boardNo` INT NULL,
  PRIMARY KEY (`articleNo`),
  INDEX `FreeBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `FreeBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`Group` (`groupNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`PracticeTestBoard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`PracticeTestBoard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(50) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` TINYINT NOT NULL,
  `groupNo` INT NULL,
  `startTime` DATETIME NULL,
  `endTime` DATETIME NULL,
  `limitTime` TIME NOT NULL,
  PRIMARY KEY (`articleNo`),
  INDEX `PracticeTestBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `PracticeTestBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`Group` (`groupNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`Grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`Grade` (
  `title` VARCHAR(100) NOT NULL,
  `level` INT NOT NULL,
  `experienceValue` INT NOT NULL,
  PRIMARY KEY (`title`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`Rank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`Rank` (
  `uid` VARCHAR(50) NOT NULL,
  `solProblem` INT NOT NULL DEFAULT 0,
  `solByCategoryProblem` INT NOT NULL DEFAULT 0,
  `registerProblem` INT NOT NULL DEFAULT 0,
  `adoptProblemSol` INT NOT NULL DEFAULT 0,
  `nickname` VARCHAR(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`ProblemSet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`ProblemSet` (
  `problemSetNo` INT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(50) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`problemSetNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`ProblemAnswer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`ProblemAnswer` (
  `problemNo` INT NOT NULL,
  `answer` TEXT NOT NULL,
  `solution` TEXT NOT NULL,
  `keyword` TEXT NULL,
  PRIMARY KEY (`problemNo`),
  CONSTRAINT `answer_problemNo_problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`Problem` (`problemNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`ProblemSetMapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`ProblemSetMapping` (
  `problemSetNo` INT NOT NULL,
  `problemNo` INT NOT NULL,
  PRIMARY KEY (`problemSetNo`, `problemNo`),
  INDEX `_problemSetNo_ProblemSet_problemSetNo_idx` (`problemSetNo` ASC) INVISIBLE,
  CONSTRAINT `ProblemSetMapping_problemSetNo_ProblemSet_problemSetNo`
    FOREIGN KEY (`problemSetNo`)
    REFERENCES `solafy`.`ProblemSet` (`problemSetNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ProblemSetMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`Problem` (`problemNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`HashTag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`HashTag` (
  `hashTagNo` INT NOT NULL,
  `hashTag` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`hashTagNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`HashTagMapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`HashTagMapping` (
  `problemNo` INT NOT NULL,
  `hashTagNo` INT NOT NULL,
  PRIMARY KEY (`problemNo`, `hashTagNo`),
  INDEX `HashTagMapping_hashTagNo_HashTag_hashTagNo_idx` (`hashTagNo` ASC) VISIBLE,
  CONSTRAINT `HashTagMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`Problem` (`problemNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `HashTagMapping_hashTagNo_HashTag_hashTagNo`
    FOREIGN KEY (`hashTagNo`)
    REFERENCES `solafy`.`HashTag` (`hashTagNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`PracticeTestMapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`PracticeTestMapping` (
  `articleNo` INT NOT NULL,
  `problemNo` INT NOT NULL,
  PRIMARY KEY (`articleNo`, `problemNo`),
  INDEX `ProblemTestMapping_problemNo_Problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  CONSTRAINT `PracticeTestMapping_articleNo_PracticeTestBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`PracticeTestBoard` (`articleNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PracticeTestMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`Problem` (`problemNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`InterestProblemMapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`InterestProblemMapping` (
  `uid` VARCHAR(50) NOT NULL,
  `problemNo` INT NOT NULL,
  PRIMARY KEY (`uid`, `problemNo`),
  INDEX `InterestProblemMapping_problemNo_Problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  CONSTRAINT `InterestProblemMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`Problem` (`problemNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`GroupMember`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`GroupMember` (
  `uid` INT NOT NULL,
  `groupNo` INT NOT NULL,
  `grade` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`uid`, `groupNo`),
  INDEX `GroupMember_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `GroupMember_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`Group` (`groupNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`GroupBoard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`GroupBoard` (
  `boardNo` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `groupNo` INT NOT NULL,
  PRIMARY KEY (`boardNo`),
  INDEX `GroupBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `GroupBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`Group` (`groupNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`ReplyByFree`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`ReplyByFree` (
  `replyNo` INT NOT NULL,
  `articleNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  INDEX `ReplyByFree_articleNo_FreeBoard_articleNo_idx` (`articleNo` ASC) VISIBLE,
  CONSTRAINT `ReplyByFree_articleNo_FreeBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`FreeBoard` (`articleNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`ReplyByAnswerModify`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`ReplyByAnswerModify` (
  `replyNo` INT NOT NULL,
  `articleNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  INDEX `ReplyByAnswerModify_articleNo_AnswerModifyBoard_articleNo_idx` (`articleNo` ASC) VISIBLE,
  CONSTRAINT `ReplyByAnswerModify_articleNo_AnswerModifyBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`AnswerModifyBoard` (`articleNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
