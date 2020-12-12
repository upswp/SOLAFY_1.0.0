-- MySQL Workbench Forward Engineering
-- drop schema solafy;
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
-- Table `solafy`.`group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`group` (
  `groupNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `type` TINYINT NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`groupNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`categoryLarge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`categoryLarge` (
  `categoryNo` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(45) NULL,
  PRIMARY KEY (`categoryNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`categoryMedium`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`categoryMedium` (
  `categoryNo` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(45) NULL,
  `categoryLargeNo` INT NULL,
  PRIMARY KEY (`categoryNo`),
  INDEX `categoryLargeNo_idx` (`categoryLargeNo` ASC) VISIBLE,
  CONSTRAINT `categoryLargeNo`
    FOREIGN KEY (`categoryLargeNo`)
    REFERENCES `solafy`.`categoryLarge` (`categoryNo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`categorySmall`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`categorySmall` (
  `categoryNo` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(45) NULL,
  `categoryMediumNo` INT NULL,
  PRIMARY KEY (`categoryNo`),
  INDEX `categoryMediumNo_idx` (`categoryMediumNo` ASC) VISIBLE,
  CONSTRAINT `categoryMediumNo`
    FOREIGN KEY (`categoryMediumNo`)
    REFERENCES `solafy`.`categoryMedium` (`categoryNo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`problem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`problem` (
  `problemNo` INT NOT NULL AUTO_INCREMENT,
  `multipleChoice` TEXT NULL DEFAULT NULL,
  `title` VARCHAR(100) NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `contents` TEXT NOT NULL,
  `starScore` DOUBLE NOT NULL DEFAULT '0',
  `categorySmallNo` INT NOT NULL,
  `type` INT NOT NULL,
  `problemcol` VARCHAR(45) NULL,
  PRIMARY KEY (`problemNo`),
  INDEX `categorySmall_idx` (`categorySmallNo` ASC) VISIBLE,
  CONSTRAINT `categorySmallNo`
    FOREIGN KEY (`categorySmallNo`)
    REFERENCES `solafy`.`categorySmall` (`categoryNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`questionboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`questionboard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `problemNo` INT NOT NULL,
  `point` INT NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` TINYINT NULL DEFAULT NULL,
  `groupNo` INT NULL DEFAULT NULL,
  PRIMARY KEY (`articleNo`),
  INDEX `questionBoard_problemNo_problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  INDEX `QuestionBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `QuestionBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE,
  CONSTRAINT `questionBoard_problemNo_problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`answerbyquestion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`answerbyquestion` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `questionNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  INDEX `Answer_questionNo_question_articleNo_idx` (`questionNo` ASC) VISIBLE,
  CONSTRAINT `Answer_questionNo_question_articleNo`
    FOREIGN KEY (`questionNo`)
    REFERENCES `solafy`.`questionboard` (`articleNo`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`answermodifyboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`answermodifyboard` (
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
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`freeboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`freeboard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `likeCount` INT NOT NULL DEFAULT '0',
  `isNotice` TINYINT NOT NULL DEFAULT '0',
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` TINYINT NOT NULL,
  `groupNo` INT NULL DEFAULT NULL,
  `boardNo` INT NULL DEFAULT NULL,
  PRIMARY KEY (`articleNo`),
  INDEX `FreeBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `FreeBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`grade` (
  `title` VARCHAR(100) NOT NULL,
  `level` INT NOT NULL,
  `experienceValue` INT NOT NULL,
  PRIMARY KEY (`title`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`groupboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`groupboard` (
  `boardNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `groupNo` INT NOT NULL,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`boardNo`),
  INDEX `GroupBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `GroupBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`groupmember`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`groupmember` (
  `uid` VARCHAR(50) NOT NULL,
  `groupNo` INT NOT NULL,
  `grade` TINYINT NOT NULL DEFAULT '1',
  PRIMARY KEY (`uid`, `groupNo`),
  INDEX `GroupMember_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `GroupMember_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`hashtag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`hashtag` (
  `hashTagNo` INT NOT NULL AUTO_INCREMENT,
  `hashTag` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`hashTagNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`hashtagmapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`hashtagmapping` (
  `problemNo` INT NOT NULL,
  `hashTagNo` INT NOT NULL,
  PRIMARY KEY (`problemNo`, `hashTagNo`),
  INDEX `HashTagMapping_hashTagNo_HashTag_hashTagNo_idx` (`hashTagNo` ASC) VISIBLE,
  CONSTRAINT `HashTagMapping_hashTagNo_HashTag_hashTagNo`
    FOREIGN KEY (`hashTagNo`)
    REFERENCES `solafy`.`hashtag` (`hashTagNo`)
    ON DELETE CASCADE,
  CONSTRAINT `HashTagMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`interestproblemmapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`interestproblemmapping` (
  `uid` VARCHAR(50) NOT NULL,
  `problemNo` INT NOT NULL,
  PRIMARY KEY (`uid`, `problemNo`),
  INDEX `InterestProblemMapping_problemNo_Problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  CONSTRAINT `InterestProblemMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`practicetestboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`practicetestboard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(50) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` TINYINT NOT NULL,
  `groupNo` INT NULL DEFAULT NULL,
  `startTime` DATETIME NULL DEFAULT NULL,
  `endTime` DATETIME NULL DEFAULT NULL,
  `limitTime` TIME NOT NULL,
  PRIMARY KEY (`articleNo`),
  INDEX `PracticeTestBoard_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `PracticeTestBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`practicetestmapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`practicetestmapping` (
  `articleNo` INT NOT NULL,
  `problemNo` INT NOT NULL,
  PRIMARY KEY (`articleNo`, `problemNo`),
  INDEX `ProblemTestMapping_problemNo_Problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  CONSTRAINT `PracticeTestMapping_articleNo_PracticeTestBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`practicetestboard` (`articleNo`)
    ON DELETE CASCADE,
  CONSTRAINT `PracticeTestMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`problemanswer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`problemanswer` (
  `problemNo` INT NOT NULL,
  `answer` TEXT NOT NULL,
  `solution` TEXT NOT NULL,
  `keyword` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`problemNo`),
  CONSTRAINT `answer_problemNo_problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`problemset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`problemset` (
  `problemSetNo` INT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(50) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`problemSetNo`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`problemsetmapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`problemsetmapping` (
  `problemSetNo` INT NOT NULL,
  `problemNo` INT NOT NULL,
  PRIMARY KEY (`problemSetNo`, `problemNo`),
  INDEX `_problemSetNo_ProblemSet_problemSetNo_idx` (`problemSetNo` ASC) INVISIBLE,
  INDEX `ProblemSetMapping_problemNo_Problem_problemNo` (`problemNo` ASC) VISIBLE,
  CONSTRAINT `ProblemSetMapping_problemNo_Problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE,
  CONSTRAINT `ProblemSetMapping_problemSetNo_ProblemSet_problemSetNo`
    FOREIGN KEY (`problemSetNo`)
    REFERENCES `solafy`.`problemset` (`problemSetNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`rank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`rank` (
  `uid` VARCHAR(50) NOT NULL,
  `solProblem` INT NOT NULL DEFAULT '0',
  `solByCategoryProblem` INT NOT NULL DEFAULT '0',
  `registerProblem` INT NOT NULL DEFAULT '0',
  `adoptProblemSol` INT NOT NULL DEFAULT '0',
  `nickname` VARCHAR(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`replybyanswermodify`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`replybyanswermodify` (
  `replyNo` INT NOT NULL AUTO_INCREMENT,
  `articleNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  INDEX `ReplyByAnswerModify_articleNo_AnswerModifyBoard_articleNo_idx` (`articleNo` ASC) VISIBLE,
  CONSTRAINT `ReplyByAnswerModify_articleNo_AnswerModifyBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`answermodifyboard` (`articleNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`replybyfree`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`replybyfree` (
  `replyNo` INT NOT NULL AUTO_INCREMENT,
  `articleNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  INDEX `ReplyByFree_articleNo_FreeBoard_articleNo_idx` (`articleNo` ASC) VISIBLE,
  CONSTRAINT `ReplyByFree_articleNo_FreeBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`freeboard` (`articleNo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
