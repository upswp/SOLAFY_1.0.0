use solafy;

-- INSERT INTO 테이블명 (COLUMN_LIST)
-- VALUES (전체 COLUMN에 넣을 VALUE_LIST);

-- Problem Table
-- 문제 대,중,소 테이블 생성 / 문제생성 날짜 컬럼 추가
-- 객관식 문제 
INSERT INTO problem (multipleChoice,title,uid,contents,categoryLarge,categoryMedium,categorySmall,type)
VALUES ('button,check,text,number','input태그','DFEIJC23WOSKXCNSWQ','다음 중 숫자만 입력 가능하게 하려면 빈칸에 무엇이 들어가야하는가?[이미지]','Web','HTML','tag',0);

-- 주관식 문제
INSERT INTO problem (title,uid,contents,categoryLarge,categoryMedium,categorySmall,type)
VALUES ('MVC패턴','DF112C23WOSKXCNSWQ','MVC패턴의 M,V,C는 각각 무엇을 의미하는지 서술하고, MVC패턴의 장점에 대해 작성하시오.','Web','패턴','MVC',1);

-- 단답식 문제 
INSERT INTO problem (title,uid,contents,categoryLarge,categoryMedium,categorySmall,type)
VALUES ('Java기초','DFEIJNGRW2SKXCNSWQ','Java는 _____프로그래밍언어이다. 빈칸에 들어가는 것은?','Java','Java','이론',2);

-- 객관식 문제2
INSERT INTO problem (multipleChoice,title,uid,contents,categoryLarge,categoryMedium,categorySmall,type)
VALUES ('Bellman-ford,Dijkstra,bfs,nCr,Floyd-Warshall','그래프 탐색 알고리즘','DFEIJJHGT7SKXCNSWQ','다음 중 그래프 탐색 알고리즘이 아닌것은?','Algorithm','그래프탐색','그래프',0);

-- 단답식 문제2
INSERT INTO problem (title,uid,contents,categoryLarge,categoryMedium,categorySmall,type)
VALUES ('Java입출력','DFEIJNGRW2SKXCNSWQ','BufferedReader br = new BufferedReader(new _______________(System.in)); 빈칸에 들어가는 것은?','Java','Java','입출력',2);

-- keyword default 값을 null로
-- Problemanswer Table
INSERT INTO problemanswer (problemNo,answer,solution)
VALUES (1,'4','숫자만 입력하게 하려면 type이 number이어야 한다.');

INSERT INTO problemanswer (problemNo,answer,solution,keyword)
VALUES (2,'M은 Model, V는 View, C는 Controller를 의미한다. MVC패턴의 장점으로는 유지보수가 수월해진다는 점이다.','MVC패턴이 무엇으로 구성되어있는지, 어떤기능이 있는지 묻는 문제입니다.','model, view, controller,유지보수,개발비용');

INSERT INTO problemanswer (problemNo,answer,solution)
VALUES (3,'객체지향','java는 객체지향프로그래밍언어이다.');

INSERT INTO problemanswer (problemNo,answer,solution)
VALUES (4,'4','nCr은 그래프 탐색 알고리즘이 아닙니다.');

INSERT INTO problemanswer (problemNo,answer,solution)
VALUES (5,'InputStreamReader','inputstreamreader가 들어가야하는 자리입니다.');

-- problemset
INSERT INTO problemset
VALUES (1,'DFEIJC23WOSKXCNSWQ','html 문제집');
INSERT INTO problemset
VALUES (2,'DF112C23WOSKXCNSWQ','Spring 문제집');
INSERT INTO problemset
VALUES (3,'DFEIJNGRW2SKXCNSWQ','Java');
INSERT INTO problemset
VALUES (4,'DFEIJHRDX8SKXCNSWQ','알고리즘');

-- problemsetmapping
INSERT INTO problemsetmapping
VALUES (1,1);
INSERT INTO problemsetmapping
VALUES (2,2);
INSERT INTO problemsetmapping
VALUES (3,3);
INSERT INTO problemsetmapping
VALUES (4,4);
INSERT INTO problemsetmapping
VALUES (3,5);

INSERT INTO grade(title,level,experienceValue)
VALUES("브론즈",10,50),
("실버",20,70),
("골드",60,30);

INSERT INTO `Group` (groupNo, title, type, description)
VALUES (1, '주희와 친구들', 0, '주희와 친구들이 함께 공부하는 그룹입니다');
-- delete from questionboard;
-- ALTER TABLE questionboard AUTO_INCREMENT = 1;
-- 문제 테이블에 problemNo 있어야 들어감
insert into questionboard (title,contents,uid,problemNo,point,isGroup,groupNo)
values("VUE관련 질문입니다.","VUE는 누가 만들었나요?","DFEIJC23WOSKXCNSWQ",1,1000,true,1),
("JS 질문입니다.","바닐라 자바스트립트에서 id를 가지고 오는 코드는 어떻게 작성하나요?","DF112C23WOSKXCNSWQ",2,1200,true,1),
("JAVA 질문입니다.","java는 누가 만들었나요? 자바가 왜 OOP인지 이유도 알려주세요.","DFEIJNGRW2SKXCNSWQ",3,1300,true,1),
("MYsql 질문입니다.","root 비밀번호가 생각나지 않아요ㅠㅠㅠ","DFEIJHRDX8SKXCNSWQ",4,1400,true,1),
("Spring 질문입니다.","스프링 부트에서 어떻게 mybatis사용하나요?","DFEIJJHGT7SKXCNSWQ",5,1500,true,1);

-- select * from questionboard;

INSERT INTO answerbyquestion (questionNo,contents,uid)
VALUES ( 1 , "당신의 답은 틀렸습니다! 그 이유는!!", "DFEIJC23WOSKXCNSWQ"),
(2 , "당신은 정말 똑똑 하군요!", "DFEIJC23WOSKXCNSWQ"),
(3 , "와우 이렇게 생각하시다니 바보같군요!", "DF112C23WOSKXCNSWQ"),
(4 , "당신 정말 수민스럽습니다!", "DF112C23WOSKXCNSWQ");

INSERT INTO answermodifyboard (uid,problemNo,uid_submitter,contents)
VALUES ("DF112C23WOSKXCNSWQ" , 1,"DFEIJC23WOSKXCNSWQ","당신의 답은 틀렸습니다! 그 이유는!!"),
("DFEIJC23WOSKXCNSWQ" , 1,"DFEIJC23WOSKXCNSWQ","정말 이게 답인가요?"),
("DF112C23WOSKXCNSWQ" , 1,"DFEIJC23WOSKXCNSWQ","당신은 이론부터 잘못됐어"),
("DFEIJC23WOSKXCNSWQ" , 1,"DFEIJC23WOSKXCNSWQ","SSAFY를 나가시는게 좋을것 같습니다."),
("DFEIJNGRW2SKXCNSWQ" , 1,"DFEIJC23WOSKXCNSWQ","정말 이게 답인가요? 장난이죠?");




INSERT INTO freeboard (title, contents, uid, likeCount, isNotice, isGroup, groupNo, boardNo)
VALUES ("이것은 테스트 프리보드0입니다.","안녕하세요 테스트 1입니다.", "DF112C23WOSKXCNSWQ", 22, 1, 0, 1, 1),
("이것은 테스트 프리보드1입니다.","안녕하세요 테스트 2입니다.", "DF112C23WOSKXCNSWQ", 101, 0, 0, 1, 2),
("이것은 테스트 프리보드2입니다.","안녕하세요 테스트 3입니다.", "DFEIJNGRW2SKXCNSWQ", 501, 0, 0, 1, 3),
("이것은 테스트 프리보드3입니다.","안녕하세요 테스트 4입니다.", "DFEIJC23WOSKXCNSWQ", 1516, 1, 0, 1, 4),
("이것은 테스트 프리보드4입니다.","안녕하세요 테스트 5입니다.", "DF112C23WOSKXCNSWQ", 2, 1, 0, 1, 5);




INSERT INTO `GroupBoard` (boardNo, title, groupNo)
VALUES (1, '자유게시판', 1);

-- *!*!*!*uid 자료형 기존 int --> varchar*!*!*!*
INSERT INTO `GroupMember` (uid, grade, groupNo)
VALUES ('DFEIJC23WOSKXCNSWQ', 0, 1),
	   ('DF112C23WOSKXCNSWQ', 1, 1),
       ('DFEIJNGRW2SKXCNSWQ', 2, 1);

INSERT INTO `HashTag` (hashTagNo, hashTag)
VALUES (1, '다이나믹 프로그래밍'),
	   (2, 'MVC 모델'),
       (3, 'Vuex');


INSERT INTO hashtagmapping(problemNo, hashTagNo)
VALUES(1, 1),
(1, 2),
(1, 3),
(2, 3),
(3, 3);



INSERT INTO interestproblemmapping(uid, problemNo)
VALUES("DFEIJC23WOSKXCNSWQ", 1),
("DFEIJC23WOSKXCNSWQ", 2),
("DF112C23WOSKXCNSWQ", 1),
("DFEIJHRDX8SKXCNSWQ", 3),
("DFEIJJHGT7SKXCNSWQ", 1);


INSERT INTO practicetestboard(uid,title,regiTime,isGroup,groupNo,startTime,endTime,limitTime)
VALUES
("DFEIJC23WOSKXCNSWQ", "JAVA 필수 개념 모의고사", NOW(), 0, null, null, null, "02:00:00"),
("DF112C23WOSKXCNSWQ", "Javascript 메서드", NOW(), 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR), "02:00:00"),
("DFEIJNGRW2SKXCNSWQ", "스프링부트 웹 개발 입문", NOW(), 0, null, null, null, "03:00:00"),
("DFEIJHRDX8SKXCNSWQ", "컴퓨터 구조론", NOW(), 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), "02:00:00");


INSERT INTO practicetestmapping(articleNo, problemNo)
VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2),
(2, 3);





use solafy;
SELECT * FROM solafy.questionboard;
SELECT * FROM solafy.rank;



-- 그냥 실행 가능
insert into solafy.rank(uid,nickname)
values("DFEIJC23WOSKXCNSWQ", "견이"),
("DF112C23WOSKXCNSWQ", "호랑돌이"),
("DFEIJNGRW2SKXCNSWQ", "쥬희"),
("DFEIJHRDX8SKXCNSWQ", "야옹"),
("DFEIJJHGT7SKXCNSWQ", "상우하좌");
update solafy.rank set 
solProblem = 100, solByCategoryProblem = 101, registerProblem = 102, adoptProblemSol = 103
where nickname = '견이';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 101, registerProblem = 5102, adoptProblemSol = 122
where nickname = '호랑돌이';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 222, registerProblem = 1102, adoptProblemSol = 155
where nickname = '쥬희';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 333, registerProblem = 2102, adoptProblemSol = 166
where nickname = '야옹';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 444, registerProblem = 3102, adoptProblemSol = 177
where nickname = '상우하좌';


-- answerModifyboard 있어야함
insert into solafy.replybyanswermodify(replyNo,articleNo,contents,uid)
values(1,1,"VXUE가 아니고 Vuex입니다만? 수정좀 부탁", "DFEIJC23WOSKXCNSWQ"),
(2,1,"답 틀린거 같습니다. 10이 답입니다.", "DF112C23WOSKXCNSWQ"),
(3,1,"주사위를 굴려 91이 나왔습니다. 답은 91입니다.", "DFEIJNGRW2SKXCNSWQ"),
(4,2,"연필을 굴렸습니다. 5가 나왔습니다. 답은 5입니다.", "DFEIJHRDX8SKXCNSWQ"),
(5,2,"답 : 27261", "DFEIJJHGT7SKXCNSWQ");

-- freeBoard 정보 있어야함
insert into solafy.replybyfree(replyNo,articleNo,contents,uid)
values(1,1, "나는 견2", "DFEIJC23WOSKXCNSWQ"),
(2,1, "나는 호랑돌이!", "DF112C23WOSKXCNSWQ"),
(3,2, "나는 쥬희!", "DFEIJNGRW2SKXCNSWQ"),
(4,2, "나는 냥집사 야옹!", "DFEIJHRDX8SKXCNSWQ"),
(5,2, "나는 상우좌아~", "DFEIJJHGT7SKXCNSWQ");



