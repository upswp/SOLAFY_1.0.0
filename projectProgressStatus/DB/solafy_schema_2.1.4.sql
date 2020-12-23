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
-- Table `solafy`.`usernickname`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`usernickname` (
  `uid` VARCHAR(50) NOT NULL,
  `nickname` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`uid`))
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
  `categoryNo` VARCHAR(10) NOT NULL,
  `type` INT NOT NULL,
  `flag` TINYINT NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`problemNo`),
  INDEX `problem_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `problem_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
  INDEX `questionboard_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `QuestionBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE,
  CONSTRAINT `questionBoard_problemNo_problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE,
  CONSTRAINT `questionboard_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
  INDEX `Answerbyquestion_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `Answer_questionNo_question_articleNo`
    FOREIGN KEY (`questionNo`)
    REFERENCES `solafy`.`questionboard` (`articleNo`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `Answerbyquestion_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`answermodifyboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`answermodifyboard` (
  `articleNo` INT NOT NULL AUTO_INCREMENT,
  `uid` VARCHAR(50) NOT NULL,
  `problemNo` INT NOT NULL,
  `uid_submitter` VARCHAR(50) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `contents` TEXT NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  INDEX `answerModifyBoard_problemNo_problem_problemNo_idx` (`problemNo` ASC) VISIBLE,
  INDEX `answermodifyboard_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  INDEX `answermodifyboard_uidsubmitter_usernickname_uid_idx` (`uid_submitter` ASC) VISIBLE,
  CONSTRAINT `answerModifyBoard_problemNo_problem_problemNo`
    FOREIGN KEY (`problemNo`)
    REFERENCES `solafy`.`problem` (`problemNo`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `answermodifyboard_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `answermodifyboard_uidsubmitter_usernickname_uid`
    FOREIGN KEY (`uid_submitter`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
  INDEX `freeboard_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `FreeBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE,
  CONSTRAINT `freeboard_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
  `statusMessage` TEXT NULL,
  `regiMessage` TEXT NULL,
  PRIMARY KEY (`uid`, `groupNo`),
  INDEX `GroupMember_groupNo_Group_groupNo_idx` (`groupNo` ASC) VISIBLE,
  CONSTRAINT `GroupMember_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE,
  CONSTRAINT `groupmember_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`hashtag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`hashtag` (
  `hashTagNo` INT NOT NULL AUTO_INCREMENT,
  `hashTag` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`hashTagNo`),
  UNIQUE INDEX `hashTag_UNIQUE` (`hashTag` ASC) VISIBLE)
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
  INDEX `practicetestboard_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `PracticeTestBoard_groupNo_Group_groupNo`
    FOREIGN KEY (`groupNo`)
    REFERENCES `solafy`.`group` (`groupNo`)
    ON DELETE CASCADE,
  CONSTRAINT `practicetestboard_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
  `flag` TINYINT NOT NULL,
  PRIMARY KEY (`problemSetNo`),
  INDEX `problemset_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `problemset_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
  PRIMARY KEY (`uid`),
  CONSTRAINT `rank_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`answermodifyreply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`answermodifyreply` (
  `replyNo` INT NOT NULL AUTO_INCREMENT,
  `articleNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  INDEX `ReplyByAnswerModify_articleNo_AnswerModifyBoard_articleNo_idx` (`articleNo` ASC) VISIBLE,
  INDEX `AnswerModifyReply_uid_UserNickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `AnswerModifyReply_articleNo_AnswerModifyBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`answermodifyboard` (`articleNo`)
    ON DELETE CASCADE,
  CONSTRAINT `AnswerModifyReply_uid_UserNickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`freereply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`freereply` (
  `replyNo` INT NOT NULL AUTO_INCREMENT,
  `articleNo` INT NOT NULL,
  `contents` TEXT NOT NULL,
  `uid` VARCHAR(50) NOT NULL,
  `regiTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  INDEX `ReplyByFree_articleNo_FreeBoard_articleNo_idx` (`articleNo` ASC) VISIBLE,
  INDEX `replybyfree_uid_usernickname_uid_idx` (`uid` ASC) VISIBLE,
  CONSTRAINT `FreeReply_articleNo_FreeBoard_articleNo`
    FOREIGN KEY (`articleNo`)
    REFERENCES `solafy`.`freeboard` (`articleNo`)
    ON DELETE CASCADE,
  CONSTRAINT `freereply_uid_usernickname_uid`
    FOREIGN KEY (`uid`)
    REFERENCES `solafy`.`usernickname` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `solafy`.`categorymedium`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`categorymedium` (
  `categoryNo` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(45) NULL,
  PRIMARY KEY (`categoryNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`categorylarge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`categorylarge` (
  `categoryNo` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(45) NULL,
  PRIMARY KEY (`categoryNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `solafy`.`categorysmall`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `solafy`.`categorysmall` (
  `categoryNo` INT NOT NULL AUTO_INCREMENT,
  `categoryName` VARCHAR(45) NULL,
  PRIMARY KEY (`categoryNo`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
