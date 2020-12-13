CREATE DATABASE  IF NOT EXISTS `solafy` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `solafy`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: solafy
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answerbyquestion`
--

DROP TABLE IF EXISTS `answerbyquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answerbyquestion` (
  `articleNo` int NOT NULL AUTO_INCREMENT,
  `questionNo` int NOT NULL,
  `contents` text NOT NULL,
  `uid` varchar(50) NOT NULL,
  `regiTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  KEY `Answer_questionNo_question_articleNo_idx` (`questionNo`),
  CONSTRAINT `Answer_questionNo_question_articleNo` FOREIGN KEY (`questionNo`) REFERENCES `questionboard` (`articleNo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answerbyquestion`
--

LOCK TABLES `answerbyquestion` WRITE;
/*!40000 ALTER TABLE `answerbyquestion` DISABLE KEYS */;
INSERT INTO `answerbyquestion` VALUES (1,1,'당신의 답은 틀렸습니다! 그 이유는!!','DFEIJC23WOSKXCNSWQ','2020-12-11 22:39:05'),(2,2,'당신은 정말 똑똑 하군요!','DFEIJC23WOSKXCNSWQ','2020-12-11 22:39:05'),(3,3,'와우 이렇게 생각하시다니 바보같군요!','DF112C23WOSKXCNSWQ','2020-12-11 22:39:05'),(4,4,'당신 정말 수민스럽습니다!','DF112C23WOSKXCNSWQ','2020-12-11 22:39:05');
/*!40000 ALTER TABLE `answerbyquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answermodifyboard`
--

DROP TABLE IF EXISTS `answermodifyboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answermodifyboard` (
  `articleNo` int NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `problemNo` int NOT NULL,
  `uid_submitter` varchar(50) NOT NULL,
  `contents` text NOT NULL,
  `regiTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleNo`),
  KEY `answerModifyBoard_problemNo_problem_problemNo_idx` (`problemNo`),
  CONSTRAINT `answerModifyBoard_problemNo_problem_problemNo` FOREIGN KEY (`problemNo`) REFERENCES `problem` (`problemNo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answermodifyboard`
--

LOCK TABLES `answermodifyboard` WRITE;
/*!40000 ALTER TABLE `answermodifyboard` DISABLE KEYS */;
INSERT INTO `answermodifyboard` VALUES (1,'DF112C23WOSKXCNSWQ',1,'DFEIJC23WOSKXCNSWQ','당신의 답은 틀렸습니다! 그 이유는!!','2020-12-11 22:39:07'),(2,'DFEIJC23WOSKXCNSWQ',1,'DFEIJC23WOSKXCNSWQ','정말 이게 답인가요?','2020-12-11 22:39:07'),(3,'DF112C23WOSKXCNSWQ',1,'DFEIJC23WOSKXCNSWQ','당신은 이론부터 잘못됐어','2020-12-11 22:39:07'),(4,'DFEIJC23WOSKXCNSWQ',1,'DFEIJC23WOSKXCNSWQ','SSAFY를 나가시는게 좋을것 같습니다.','2020-12-11 22:39:07'),(5,'DFEIJNGRW2SKXCNSWQ',1,'DFEIJC23WOSKXCNSWQ','정말 이게 답인가요? 장난이죠?','2020-12-11 22:39:07');
/*!40000 ALTER TABLE `answermodifyboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freeboard`
--

DROP TABLE IF EXISTS `freeboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freeboard` (
  `articleNo` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `contents` text NOT NULL,
  `uid` varchar(50) NOT NULL,
  `likeCount` int NOT NULL DEFAULT '0',
  `isNotice` tinyint NOT NULL DEFAULT '0',
  `regiTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` tinyint NOT NULL,
  `groupNo` int DEFAULT NULL,
  `boardNo` int DEFAULT NULL,
  PRIMARY KEY (`articleNo`),
  KEY `FreeBoard_groupNo_Group_groupNo_idx` (`groupNo`),
  CONSTRAINT `FreeBoard_groupNo_Group_groupNo` FOREIGN KEY (`groupNo`) REFERENCES `group` (`groupNo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freeboard`
--

LOCK TABLES `freeboard` WRITE;
/*!40000 ALTER TABLE `freeboard` DISABLE KEYS */;
INSERT INTO `freeboard` VALUES (1,'이것은 테스트 프리보드0입니다.','안녕하세요 테스트 1입니다.','DF112C23WOSKXCNSWQ',22,1,'2020-12-11 22:39:29',0,1,1),(2,'이것은 테스트 프리보드1입니다.','안녕하세요 테스트 2입니다.','DF112C23WOSKXCNSWQ',101,0,'2020-12-11 22:39:29',0,1,2),(3,'이것은 테스트 프리보드2입니다.','안녕하세요 테스트 3입니다.','DFEIJNGRW2SKXCNSWQ',501,0,'2020-12-11 22:39:29',0,1,3),(4,'이것은 테스트 프리보드3입니다.','안녕하세요 테스트 4입니다.','DFEIJC23WOSKXCNSWQ',1516,1,'2020-12-11 22:39:29',0,1,4),(5,'이것은 테스트 프리보드4입니다.','안녕하세요 테스트 5입니다.','DF112C23WOSKXCNSWQ',2,1,'2020-12-11 22:39:29',0,1,5);
/*!40000 ALTER TABLE `freeboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grade` (
  `title` varchar(100) NOT NULL,
  `level` int NOT NULL,
  `experienceValue` int NOT NULL,
  PRIMARY KEY (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
INSERT INTO `grade` VALUES ('골드',60,30),('브론즈',10,50),('실버',20,70);
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group` (
  `groupNo` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `type` tinyint NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`groupNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (1,'주희와 친구들',0,'주희와 친구들이 함께 공부하는 그룹입니다');
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupboard`
--

DROP TABLE IF EXISTS `groupboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupboard` (
  `boardNo` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `groupNo` int NOT NULL,
  PRIMARY KEY (`boardNo`),
  KEY `GroupBoard_groupNo_Group_groupNo_idx` (`groupNo`),
  CONSTRAINT `GroupBoard_groupNo_Group_groupNo` FOREIGN KEY (`groupNo`) REFERENCES `group` (`groupNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupboard`
--

LOCK TABLES `groupboard` WRITE;
/*!40000 ALTER TABLE `groupboard` DISABLE KEYS */;
INSERT INTO `groupboard` VALUES (1,'자유게시판',1);
/*!40000 ALTER TABLE `groupboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupmember`
--

DROP TABLE IF EXISTS `groupmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupmember` (
  `uid` varchar(50) NOT NULL,
  `groupNo` int NOT NULL,
  `grade` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`uid`,`groupNo`),
  KEY `GroupMember_groupNo_Group_groupNo_idx` (`groupNo`),
  CONSTRAINT `GroupMember_groupNo_Group_groupNo` FOREIGN KEY (`groupNo`) REFERENCES `group` (`groupNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupmember`
--

LOCK TABLES `groupmember` WRITE;
/*!40000 ALTER TABLE `groupmember` DISABLE KEYS */;
INSERT INTO `groupmember` VALUES ('DF112C23WOSKXCNSWQ',1,1),('DFEIJC23WOSKXCNSWQ',1,0),('DFEIJNGRW2SKXCNSWQ',1,2);
/*!40000 ALTER TABLE `groupmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtag` (
  `hashTagNo` int NOT NULL,
  `hashTag` varchar(20) NOT NULL,
  PRIMARY KEY (`hashTagNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag`
--

LOCK TABLES `hashtag` WRITE;
/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
INSERT INTO `hashtag` VALUES (1,'다이나믹 프로그래밍'),(2,'MVC 모델'),(3,'Vuex');
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtagmapping`
--

DROP TABLE IF EXISTS `hashtagmapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtagmapping` (
  `problemNo` int NOT NULL,
  `hashTagNo` int NOT NULL,
  PRIMARY KEY (`problemNo`,`hashTagNo`),
  KEY `HashTagMapping_hashTagNo_HashTag_hashTagNo_idx` (`hashTagNo`),
  CONSTRAINT `HashTagMapping_hashTagNo_HashTag_hashTagNo` FOREIGN KEY (`hashTagNo`) REFERENCES `hashtag` (`hashTagNo`),
  CONSTRAINT `HashTagMapping_problemNo_Problem_problemNo` FOREIGN KEY (`problemNo`) REFERENCES `problem` (`problemNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtagmapping`
--

LOCK TABLES `hashtagmapping` WRITE;
/*!40000 ALTER TABLE `hashtagmapping` DISABLE KEYS */;
INSERT INTO `hashtagmapping` VALUES (1,1),(1,2),(1,3),(2,3),(3,3);
/*!40000 ALTER TABLE `hashtagmapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interestproblemmapping`
--

DROP TABLE IF EXISTS `interestproblemmapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interestproblemmapping` (
  `uid` varchar(50) NOT NULL,
  `problemNo` int NOT NULL,
  PRIMARY KEY (`uid`,`problemNo`),
  KEY `InterestProblemMapping_problemNo_Problem_problemNo_idx` (`problemNo`),
  CONSTRAINT `InterestProblemMapping_problemNo_Problem_problemNo` FOREIGN KEY (`problemNo`) REFERENCES `problem` (`problemNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interestproblemmapping`
--

LOCK TABLES `interestproblemmapping` WRITE;
/*!40000 ALTER TABLE `interestproblemmapping` DISABLE KEYS */;
INSERT INTO `interestproblemmapping` VALUES ('DF112C23WOSKXCNSWQ',1),('DFEIJC23WOSKXCNSWQ',1),('DFEIJJHGT7SKXCNSWQ',1),('DFEIJC23WOSKXCNSWQ',2),('DFEIJHRDX8SKXCNSWQ',3);
/*!40000 ALTER TABLE `interestproblemmapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `practicetestboard`
--

DROP TABLE IF EXISTS `practicetestboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `practicetestboard` (
  `articleNo` int NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `regiTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` tinyint NOT NULL,
  `groupNo` int DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `limitTime` time NOT NULL,
  PRIMARY KEY (`articleNo`),
  KEY `PracticeTestBoard_groupNo_Group_groupNo_idx` (`groupNo`),
  CONSTRAINT `PracticeTestBoard_groupNo_Group_groupNo` FOREIGN KEY (`groupNo`) REFERENCES `group` (`groupNo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `practicetestboard`
--

LOCK TABLES `practicetestboard` WRITE;
/*!40000 ALTER TABLE `practicetestboard` DISABLE KEYS */;
INSERT INTO `practicetestboard` VALUES (1,'DFEIJC23WOSKXCNSWQ','JAVA 필수 개념 모의고사','2020-12-11 22:40:02',0,NULL,NULL,NULL,'02:00:00'),(2,'DF112C23WOSKXCNSWQ','Javascript 메서드','2020-12-11 22:40:02',1,1,'2020-12-11 22:40:02','2020-12-12 00:40:02','02:00:00'),(3,'DFEIJNGRW2SKXCNSWQ','스프링부트 웹 개발 입문','2020-12-11 22:40:02',0,NULL,NULL,NULL,'03:00:00'),(4,'DFEIJHRDX8SKXCNSWQ','컴퓨터 구조론','2020-12-11 22:40:02',1,1,'2020-12-11 22:40:02','2020-12-11 23:40:02','02:00:00');
/*!40000 ALTER TABLE `practicetestboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `practicetestmapping`
--

DROP TABLE IF EXISTS `practicetestmapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `practicetestmapping` (
  `articleNo` int NOT NULL,
  `problemNo` int NOT NULL,
  PRIMARY KEY (`articleNo`,`problemNo`),
  KEY `ProblemTestMapping_problemNo_Problem_problemNo_idx` (`problemNo`),
  CONSTRAINT `PracticeTestMapping_articleNo_PracticeTestBoard_articleNo` FOREIGN KEY (`articleNo`) REFERENCES `practicetestboard` (`articleNo`),
  CONSTRAINT `PracticeTestMapping_problemNo_Problem_problemNo` FOREIGN KEY (`problemNo`) REFERENCES `problem` (`problemNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `practicetestmapping`
--

LOCK TABLES `practicetestmapping` WRITE;
/*!40000 ALTER TABLE `practicetestmapping` DISABLE KEYS */;
INSERT INTO `practicetestmapping` VALUES (1,1),(2,1),(2,2),(1,3),(2,3);
/*!40000 ALTER TABLE `practicetestmapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `problem` (
  `problemNo` int NOT NULL AUTO_INCREMENT,
  `multipleChoice` text,
  `title` varchar(100) NOT NULL,
  `uid` varchar(50) NOT NULL,
  `contents` text NOT NULL,
  `starScore` double NOT NULL DEFAULT '0',
  `categoryLarge` varchar(45) NOT NULL,
  `categoryMedium` varchar(45) NOT NULL,
  `categorySmall` varchar(45) NOT NULL,
  `type` int NOT NULL,
  PRIMARY KEY (`problemNo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (1,'button,check,text,number','input태그','DFEIJC23WOSKXCNSWQ','다음 중 숫자만 입력 가능하게 하려면 빈칸에 무엇이 들어가야하는가?[이미지]',0,'Web','HTML','tag',0),(2,NULL,'MVC패턴','DF112C23WOSKXCNSWQ','MVC패턴의 M,V,C는 각각 무엇을 의미하는지 서술하고, MVC패턴의 장점에 대해 작성하시오.',0,'Web','패턴','MVC',1),(3,NULL,'Java기초','DFEIJNGRW2SKXCNSWQ','Java는 _____프로그래밍언어이다. 빈칸에 들어가는 것은?',0,'Java','Java','이론',2),(4,'Bellman-ford,Dijkstra,bfs,nCr,Floyd-Warshall','그래프 탐색 알고리즘','DFEIJJHGT7SKXCNSWQ','다음 중 그래프 탐색 알고리즘이 아닌것은?',0,'Algorithm','그래프탐색','그래프',0),(5,NULL,'Java입출력','DFEIJNGRW2SKXCNSWQ','BufferedReader br = new BufferedReader(new _______________(System.in)); 빈칸에 들어가는 것은?',0,'Java','Java','입출력',2);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problemanswer`
--

DROP TABLE IF EXISTS `problemanswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `problemanswer` (
  `problemNo` int NOT NULL,
  `answer` text NOT NULL,
  `solution` text NOT NULL,
  `keyword` text,
  PRIMARY KEY (`problemNo`),
  CONSTRAINT `answer_problemNo_problem_problemNo` FOREIGN KEY (`problemNo`) REFERENCES `problem` (`problemNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problemanswer`
--

LOCK TABLES `problemanswer` WRITE;
/*!40000 ALTER TABLE `problemanswer` DISABLE KEYS */;
INSERT INTO `problemanswer` VALUES (1,'4','숫자만 입력하게 하려면 type이 number이어야 한다.',NULL),(2,'M은 Model, V는 View, C는 Controller를 의미한다. MVC패턴의 장점으로는 유지보수가 수월해진다는 점이다.','MVC패턴이 무엇으로 구성되어있는지, 어떤기능이 있는지 묻는 문제입니다.','model, view, controller,유지보수,개발비용'),(3,'객체지향','java는 객체지향프로그래밍언어이다.',NULL),(4,'4','nCr은 그래프 탐색 알고리즘이 아닙니다.',NULL),(5,'InputStreamReader','inputstreamreader가 들어가야하는 자리입니다.',NULL);
/*!40000 ALTER TABLE `problemanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problemset`
--

DROP TABLE IF EXISTS `problemset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `problemset` (
  `problemSetNo` int NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`problemSetNo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problemset`
--

LOCK TABLES `problemset` WRITE;
/*!40000 ALTER TABLE `problemset` DISABLE KEYS */;
INSERT INTO `problemset` VALUES (1,'DFEIJC23WOSKXCNSWQ','html 문제집'),(2,'DF112C23WOSKXCNSWQ','Spring 문제집'),(3,'DFEIJNGRW2SKXCNSWQ','Java'),(4,'DFEIJHRDX8SKXCNSWQ','알고리즘');
/*!40000 ALTER TABLE `problemset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problemsetmapping`
--

DROP TABLE IF EXISTS `problemsetmapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `problemsetmapping` (
  `problemSetNo` int NOT NULL,
  `problemNo` int NOT NULL,
  PRIMARY KEY (`problemSetNo`,`problemNo`),
  KEY `_problemSetNo_ProblemSet_problemSetNo_idx` (`problemSetNo`) /*!80000 INVISIBLE */,
  KEY `ProblemSetMapping_problemNo_Problem_problemNo` (`problemNo`),
  CONSTRAINT `ProblemSetMapping_problemNo_Problem_problemNo` FOREIGN KEY (`problemNo`) REFERENCES `problem` (`problemNo`),
  CONSTRAINT `ProblemSetMapping_problemSetNo_ProblemSet_problemSetNo` FOREIGN KEY (`problemSetNo`) REFERENCES `problemset` (`problemSetNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problemsetmapping`
--

LOCK TABLES `problemsetmapping` WRITE;
/*!40000 ALTER TABLE `problemsetmapping` DISABLE KEYS */;
INSERT INTO `problemsetmapping` VALUES (1,1),(2,2),(3,3),(4,4),(3,5);
/*!40000 ALTER TABLE `problemsetmapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionboard`
--

DROP TABLE IF EXISTS `questionboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionboard` (
  `articleNo` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `contents` text NOT NULL,
  `uid` varchar(50) NOT NULL,
  `problemNo` int NOT NULL,
  `point` int NOT NULL,
  `regiTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isGroup` tinyint DEFAULT NULL,
  `groupNo` int DEFAULT NULL,
  PRIMARY KEY (`articleNo`),
  KEY `questionBoard_problemNo_problem_problemNo_idx` (`problemNo`),
  KEY `QuestionBoard_groupNo_Group_groupNo_idx` (`groupNo`),
  CONSTRAINT `QuestionBoard_groupNo_Group_groupNo` FOREIGN KEY (`groupNo`) REFERENCES `group` (`groupNo`),
  CONSTRAINT `questionBoard_problemNo_problem_problemNo` FOREIGN KEY (`problemNo`) REFERENCES `problem` (`problemNo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionboard`
--

LOCK TABLES `questionboard` WRITE;
/*!40000 ALTER TABLE `questionboard` DISABLE KEYS */;
INSERT INTO `questionboard` VALUES (1,'VUE관련 질문입니다.','VUE는 누가 만들었나요?','DFEIJC23WOSKXCNSWQ',1,1000,'2020-12-11 22:39:01',1,1),(2,'JS 질문입니다.','바닐라 자바스트립트에서 id를 가지고 오는 코드는 어떻게 작성하나요?','DF112C23WOSKXCNSWQ',2,1200,'2020-12-11 22:39:01',1,1),(3,'JAVA 질문입니다.','java는 누가 만들었나요? 자바가 왜 OOP인지 이유도 알려주세요.','DFEIJNGRW2SKXCNSWQ',3,1300,'2020-12-11 22:39:01',1,1),(4,'MYsql 질문입니다.','root 비밀번호가 생각나지 않아요ㅠㅠㅠ','DFEIJHRDX8SKXCNSWQ',4,1400,'2020-12-11 22:39:01',1,1),(5,'Spring 질문입니다.','스프링 부트에서 어떻게 mybatis사용하나요?','DFEIJJHGT7SKXCNSWQ',5,1500,'2020-12-11 22:39:01',1,1);
/*!40000 ALTER TABLE `questionboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rank`
--

DROP TABLE IF EXISTS `rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rank` (
  `uid` varchar(50) NOT NULL,
  `solProblem` int NOT NULL DEFAULT '0',
  `solByCategoryProblem` int NOT NULL DEFAULT '0',
  `registerProblem` int NOT NULL DEFAULT '0',
  `adoptProblemSol` int NOT NULL DEFAULT '0',
  `nickname` varchar(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rank`
--

LOCK TABLES `rank` WRITE;
/*!40000 ALTER TABLE `rank` DISABLE KEYS */;
INSERT INTO `rank` VALUES ('DF112C23WOSKXCNSWQ',0,0,0,0,'호랑돌이'),('DFEIJC23WOSKXCNSWQ',0,0,0,0,'견이'),('DFEIJHRDX8SKXCNSWQ',0,0,0,0,'야옹'),('DFEIJJHGT7SKXCNSWQ',100,444,3102,177,'상우하좌'),('DFEIJNGRW2SKXCNSWQ',0,0,0,0,'쥬희');
/*!40000 ALTER TABLE `rank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `replybyanswermodify`
--

DROP TABLE IF EXISTS `replybyanswermodify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `replybyanswermodify` (
  `replyNo` int NOT NULL,
  `articleNo` int NOT NULL,
  `contents` text NOT NULL,
  `uid` varchar(50) NOT NULL,
  `regiTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  KEY `ReplyByAnswerModify_articleNo_AnswerModifyBoard_articleNo_idx` (`articleNo`),
  CONSTRAINT `ReplyByAnswerModify_articleNo_AnswerModifyBoard_articleNo` FOREIGN KEY (`articleNo`) REFERENCES `answermodifyboard` (`articleNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replybyanswermodify`
--

LOCK TABLES `replybyanswermodify` WRITE;
/*!40000 ALTER TABLE `replybyanswermodify` DISABLE KEYS */;
INSERT INTO `replybyanswermodify` VALUES (1,1,'VXUE가 아니고 Vuex입니다만? 수정좀 부탁','DFEIJC23WOSKXCNSWQ','2020-12-11 22:41:15'),(2,1,'답 틀린거 같습니다. 10이 답입니다.','DF112C23WOSKXCNSWQ','2020-12-11 22:41:15'),(3,1,'주사위를 굴려 91이 나왔습니다. 답은 91입니다.','DFEIJNGRW2SKXCNSWQ','2020-12-11 22:41:15'),(4,2,'연필을 굴렸습니다. 5가 나왔습니다. 답은 5입니다.','DFEIJHRDX8SKXCNSWQ','2020-12-11 22:41:15'),(5,2,'답 : 27261','DFEIJJHGT7SKXCNSWQ','2020-12-11 22:41:15');
/*!40000 ALTER TABLE `replybyanswermodify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `replybyfree`
--

DROP TABLE IF EXISTS `replybyfree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `replybyfree` (
  `replyNo` int NOT NULL,
  `articleNo` int NOT NULL,
  `contents` text NOT NULL,
  `uid` varchar(50) NOT NULL,
  `regiTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`replyNo`),
  KEY `ReplyByFree_articleNo_FreeBoard_articleNo_idx` (`articleNo`),
  CONSTRAINT `ReplyByFree_articleNo_FreeBoard_articleNo` FOREIGN KEY (`articleNo`) REFERENCES `freeboard` (`articleNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `replybyfree`
--

LOCK TABLES `replybyfree` WRITE;
/*!40000 ALTER TABLE `replybyfree` DISABLE KEYS */;
INSERT INTO `replybyfree` VALUES (1,1,'나는 견2','DFEIJC23WOSKXCNSWQ','2020-12-11 22:41:20'),(2,1,'나는 호랑돌이!','DF112C23WOSKXCNSWQ','2020-12-11 22:41:20'),(3,2,'나는 쥬희!','DFEIJNGRW2SKXCNSWQ','2020-12-11 22:41:20'),(4,2,'나는 냥집사 야옹!','DFEIJHRDX8SKXCNSWQ','2020-12-11 22:41:20'),(5,2,'나는 상우좌아~','DFEIJJHGT7SKXCNSWQ','2020-12-11 22:41:20');
/*!40000 ALTER TABLE `replybyfree` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-12 20:11:52
