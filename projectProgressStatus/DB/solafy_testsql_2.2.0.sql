use solafy;
-- categorylarge Table
INSERT INTO categorylarge (categoryName)
VALUES('Web'),('Java'),('Algorithm');
-- categorymedium Table
INSERT INTO categorymedium (categoryName)
VALUES('HTML'),('Pattern'),('Java'),('그래프탐색');

-- categorysmall Table
INSERT INTO categorysmall (categoryName)
VALUES('tag'),('MVC'),('이론'),('그래프'),('입출력');

-- categorylmmapping Table
INSERT INTO categorylmmapping (largeCategoryNo,mediumCategoryNo)
VALUES(1,1),(1,2),(2,2),(3,4);

-- categorymsmapping Table
INSERT INTO categorymsmapping (mediumCategoryNo,smallCategoryNo)
VALUES(1,1),(2,2),(3,3),(4,4),(3,5);

-- usernickname Table
INSERT INTO usernickname(uid, nickname)
VALUES
('DFEIJC23WOSKXCNSWQ', '호랑돌이'),
('DF112C23WOSKXCNSWQ', 'A~용'),
('DFEIJNGRW2SKXCNSWQ', '견2'),
('DFEIJHRDX8SKXCNSWQ', '상우하좌'),
('DFEIJJHGT7SKXCNSWQ', '내가바로주희다');

-- Problem Table
-- 문제 대,중,소 테이블 생성 / 문제생성 날짜 컬럼 추가
-- 객관식 문제 
INSERT INTO problem (multipleChoice,title,uid,contents,categoryNo,type,flag)
VALUES ('button,check,text,number','input태그','DFEIJC23WOSKXCNSWQ','다음 중 숫자만 입력 가능하게 하려면 빈칸에 무엇이 들어가야하는가?[이미지]','0100100001',0,1);

-- 주관식 문제
INSERT INTO problem (title,uid,contents,categoryNo,type,flag)
VALUES ('MVC패턴','DF112C23WOSKXCNSWQ','MVC패턴의 M,V,C는 각각 무엇을 의미하는지 서술하고, MVC패턴의 장점에 대해 작성하시오.','0100100002',1,1);

-- 단답식 문제 
INSERT INTO problem (title,uid,contents,categoryNo,type,flag)
VALUES ('Java기초','DFEIJNGRW2SKXCNSWQ','Java는 _____프로그래밍언어이다. 빈칸에 들어가는 것은?','0200100003',2,1);


-- 객관식 문제2
INSERT INTO problem (multipleChoice,title,uid,contents,categoryNo,type,flag)
VALUES ('Bellman-ford,Dijkstra,bfs,nCr,Floyd-Warshall','그래프 탐색 알고리즘','DFEIJJHGT7SKXCNSWQ','다음 중 그래프 탐색 알고리즘이 아닌것은?','0200200003',0,1);

-- 단답식 문제2
INSERT INTO problem (title,uid,contents,categoryNo,type,flag)
VALUES ('Java입출력','DFEIJNGRW2SKXCNSWQ','BufferedReader br = new BufferedReader(new _______________(System.in)); 빈칸에 들어가는 것은?','0300100004',2,1);

-- 주관식 문제 2
INSERT INTO problem (title,uid,contents,categoryNo,type,flag)
VALUES ('title1','DFEIJC23WOSKXCNSWQ','content1','0200100003',1,1);

-- 주관식 문제 3
INSERT INTO problem (title,uid,contents,categoryNo,type,flag)
VALUES ('title2','DFEIJC23WOSKXCNSWQ','content2','0100100001',1,1);

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

INSERT INTO problemanswer (problemNo,answer,solution,keyword)
VALUES (6,'answer1','solution1','keyword1,keyword2,keyword3,keyword4');

INSERT INTO problemanswer (problemNo,answer,solution,keyword)
VALUES (7,'answer2','solution2','keyword1,keyword2,keyword5,keyword6');

-- ------------------------------------------------------------------현재 성공한 부분
-- problemset
INSERT INTO problemset (uid,title,flag)
VALUES ('DFEIJC23WOSKXCNSWQ','html 문제집',1);
INSERT INTO problemset (uid,title,flag)
VALUES ('DF112C23WOSKXCNSWQ','Spring 문제집',1);
INSERT INTO problemset (uid,title,flag)
VALUES ('DFEIJNGRW2SKXCNSWQ','Java',1);
INSERT INTO problemset (uid,title,flag)
VALUES ('DFEIJHRDX8SKXCNSWQ','알고리즘',1);

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
INSERT INTO problemsetmapping
VALUES (1,6);
INSERT INTO problemsetmapping
VALUES (1,7);

INSERT INTO grade(title,level,experienceValue)
VALUES("브론즈",10,50),
("실버",20,70),
("골드",60,30);

INSERT INTO `group` (title, type, description)
VALUES ('주희와 친구들', 0, '주희와 친구들이 함께 공부하는 그룹입니다'),
('상우의 친구들', 0, '상우의 친구들이 함께 공부하는 그룹입니다'),
('범석 없는 범석팀', 1, '범석 빼고 범석 친구들만 모인 그룹');

-- 문제 테이블에 problemNo 있어야 들어감
insert into questionboard (title,contents,uid,problemNo,point,isGroup,groupNo)
values
("VUE관련 질문입니다.","VUE는 누가 만group들었나요?","DFEIJC23WOSKXCNSWQ",1,1000,true,1),
("JS 질문입니다.","바닐라 자바스트립트에서 id를 가지고 오는 코드는 어떻게 작성하나요?","DF112C23WOSKXCNSWQ",2,1200,true,2),
("JAVA 질문입니다.","java는 누가 만들었나요? 자바가 왜 OOP인지 이유도 알려주세요.","DFEIJNGRW2SKXCNSWQ",3,1300,true,1),
("MYsql 질문입니다.","root 비밀번호가 생각나지 않아요ㅠㅠㅠ","DFEIJHRDX8SKXCNSWQ",4,1400,true,1),
("Spring 질문입니다.","스프링 부트에서 어떻게 mybatis사용하나요?","DFEIJJHGT7SKXCNSWQ",5,1500,true,2),
("6번 질문입니다.","질문 6번","DFEIJHRDX8SKXCNSWQ",6,1400,true,1),
("7번 질문입니다.","질문 7번","DFEIJJHGT7SKXCNSWQ",7,1500,true,2),
("8번 질문입니다.","질문 8번","DFEIJHRDX8SKXCNSWQ",6,1400,true,1),
("9번 질문입니다.","질문 9번","DFEIJJHGT7SKXCNSWQ",7,1500,true,2),
("10번 질문입니다.","질문 10번","DFEIJHRDX8SKXCNSWQ",6,1400,true,1);
-- select * from questionboard;

INSERT INTO answerbyquestion (questionNo,contents,uid)
VALUES ( 1 , "당신의 답은 틀렸습니다! 그 이유는!!", "DFEIJC23WOSKXCNSWQ"),
(2 , "당신은 정말 똑똑 하군요!", "DFEIJC23WOSKXCNSWQ"),
(3 , "와우 이렇게 생각하시다니 바보같군요!", "DF112C23WOSKXCNSWQ"),
(4 , "당신 정말 주희스럽습니다!", "DF112C23WOSKXCNSWQ"),
(2 , "당신 정말 상우스럽습니다!", "DF112C23WOSKXCNSWQ"),
(4 , "당신 정말 기현스럽습니다!", "DF112C23WOSKXCNSWQ"),
(1 , "당신 정말 아영스럽습니다!", "DF112C23WOSKXCNSWQ");

INSERT INTO answermodifyboard (uid,problemNo,uid_submitter,title,contents)
VALUES ("DF112C23WOSKXCNSWQ" , 1,"DF112C23WOSKXCNSWQ","이유는 알려주지 않지","당신의 답은 틀렸습니다! 그 이유는!!"),
("DFEIJC23WOSKXCNSWQ" , 2,"DFEIJC23WOSKXCNSWQ","납득 못해","정말 이게 답인가요?"),
("DF112C23WOSKXCNSWQ" , 3,"DFEIJC23WOSKXCNSWQ","이건 근본 부터","당신은 이론부터 잘못됐어"),
("DFEIJC23WOSKXCNSWQ" , 1,"DFEIJC23WOSKXCNSWQ","새로운 미래","SSAFY를 나가시는게 좋을것 같습니다."),
("DFEIJNGRW2SKXCNSWQ" , 4,"DF112C23WOSKXCNSWQ","장난인가","정말 이게 답인가요? 장난이죠?"),
("DFEIJNGRW2SKXCNSWQ" , 1,"DFEIJJHGT7SKXCNSWQ","이게 어딜봐서 정답일까요","정말 이게 답인가요? 장난이죠?"),
("DFEIJNGRW2SKXCNSWQ" , 6,"DF112C23WOSKXCNSWQ","내 사전에 없는 답인데","정말 이게 답인가요? 장난이죠?"),
("DFEIJNGRW2SKXCNSWQ" , 1,"DFEIJJHGT7SKXCNSWQ","어제 밤샌게 뭐가 되나요","정말 이게 답인가요? 장난이죠?"),
("DFEIJNGRW2SKXCNSWQ" , 7,"DF112C23WOSKXCNSWQ","어제 교수님께 여쭈어 봤는데","정말 이게 답인가요? 장난이죠?");




INSERT INTO freeboard (title, contents, uid, likeCount, isNotice, isGroup, groupNo, boardNo)
VALUES ("이것은 테스트 프리보드0입니다.","안녕하세요 테스트 1입니다.", "DFEIJC23WOSKXCNSWQ", 22, 1, 0, 1, 1),
("이것은 테스트 프리보드1입니다.","안녕하세요 테스트 2입니다.", "DF112C23WOSKXCNSWQ", 101, 0, 0, 1, 2),
("이것은 테스트 프리보드2입니다.","안녕하세요 테스트 3입니다.", "DFEIJC23WOSKXCNSWQ", 501, 0, 0, 1, 3),
("이것은 테스트 프리보드3입니다.","안녕하세요 테스트 4입니다.", "DF112C23WOSKXCNSWQ", 1516, 1, 0, 1, 4),
("이것은 테스트 프리보드4입니다.","안녕하세요 테스트 5입니다.", "DFEIJC23WOSKXCNSWQ", 2, 1, 0, 1, 5),
("이것은 테스트 프리보드5입니다.","안녕하세요 테스트 6입니다.", "DF112C23WOSKXCNSWQ", 100, 0, 0, 2, 1),
("이것은 테스트 프리보드6입니다.","안녕하세요 테스트 7입니다.", "DF112C23WOSKXCNSWQ", 777, 1, 0, 2, 2),
("이것은 테스트 프리보드7입니다.","안녕하세요 테스트 8입니다.", "DFEIJNGRW2SKXCNSWQ", 700, 0, 0, 2, 3),
("이것은 테스트 프리보드8입니다.","안녕하세요 테스트 9입니다.", "DFEIJJHGT7SKXCNSWQ", 5, 1, 0, 2, 4),
("이것은 테스트 프리보드9입니다.","안녕하세요 테스트 10입니다.", "DFEIJNGRW2SKXCNSWQ", 999, 0, 0, 2, 5);




INSERT INTO groupboard (title, groupNo, description)
VALUES ('자유게시판', 1, '자유롭게 이야기를 나눠보세요'),
	('공지사항', 1, '자주 확인하삼'),
        ('게시판', 2, '그냥.... 게시판...'),
        ('범석겟판', 3, '범석 씨에 관한 이야기를 하자');

truncate groupmember;
INSERT INTO groupmember (uid, grade, groupNo, statusMessage, regiMessage)
VALUES ('DFEIJNGRW2SKXCNSWQ', 0, 1, '나는... 고독한 한마리의 야옹이','가입신청합니다.'),
	   ('DFEIJHRDX8SKXCNSWQ', 1, 1, '나는... 상우','가입신청합니다.'),
       ('DFEIJJHGT7SKXCNSWQ', 2, 1, '나는... ㅎㅎㅎㅎㅎㅎ','ㅎㅎㅎㅎㅎㅎ'),
       ('DFEIJJHGT7SKXCNSWQ', 0, 2, '오늘도 화이팅','가입 부탁..ㅠ'),
       ('DF112C23WOSKXCNSWQ', 1, 2, '내일도 화이팅','나는 호랑돌'),
       ('DFEIJC23WOSKXCNSWQ', 2, 2, '아몰랑','그룹 접수하러 왔음'),
       ('DFEIJC23WOSKXCNSWQ', 0, 3, '알고리즘 화이팅','그룹장 친구 3'),
       ('DFEIJHRDX8SKXCNSWQ', 1, 3, '뷰 화이팅','그룹장 친구'),
       ('DFEIJJHGT7SKXCNSWQ', 1, 3, 'ㅎㅎㅎㅎㅎㅎㅎㅎ','그룹 가입?');


INSERT INTO hashtag (hashTag)
VALUES ('다이나믹 프로그래밍'), ('jQuery'), ('selector'),
	   ('MVC 모델'), ('다익스트라'),
       ('Vuex');

INSERT INTO hashtagmapping(problemNo, hashTagNo)
VALUES
(1, 1),(1, 2),(1, 3),
(2, 3),(2, 4),
(3, 1),
(4, 5),
(5, 3),
(6, 5),
(7, 4);

INSERT INTO interestproblemmapping(uid, problemNo)
VALUES("DFEIJC23WOSKXCNSWQ", 1),
("DFEIJC23WOSKXCNSWQ", 2),
("DF112C23WOSKXCNSWQ", 3),
("DF112C23WOSKXCNSWQ", 1),
("DFEIJNGRW2SKXCNSWQ", 4),
("DFEIJNGRW2SKXCNSWQ", 5),
("DFEIJNGRW2SKXCNSWQ", 7),
("DFEIJHRDX8SKXCNSWQ", 1),
("DFEIJHRDX8SKXCNSWQ", 7),
("DFEIJHRDX8SKXCNSWQ", 2),
("DFEIJJHGT7SKXCNSWQ", 3);

INSERT INTO practicetestboard(uid,title,regiTime,isGroup,groupNo,startTime,endTime,limitTime)
VALUES
("DFEIJC23WOSKXCNSWQ", "JAVA 필수 개념 모의고사", NOW(), 0, null, null, null, "02:00:00"),
("DF112C23WOSKXCNSWQ", "Javascript 메서드", NOW(), 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR), "02:00:00"),
("DFEIJNGRW2SKXCNSWQ", "스프링부트 웹 개발 입문", NOW(), 0, null, null, null, "03:00:00"),
("DFEIJHRDX8SKXCNSWQ", "컴퓨터 구조론", NOW(), 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), "02:00:00"),
("DFEIJJHGT7SKXCNSWQ", "모던 웹을 위한 HTML5와CSS 바이블", NOW(), 1, 2, NOW(), DATE_ADD(NOW(), INTERVAL 2 HOUR), "02:30:00"),
("DF112C23WOSKXCNSWQ", "알고리즘 설계와 기초", NOW(), 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 4 HOUR), "01:20:00"),
("DF112C23WOSKXCNSWQ", "인명론", NOW(), 0, null, NOW(), DATE_ADD(NOW(), INTERVAL 3 HOUR), "02:40:00");

INSERT INTO practicetestmapping(articleNo, problemNo)
VALUES
(1, 1),(1, 3),
(2, 1),(2, 2),(2, 3),
(3, 7),(3, 1),
(4, 2),(4, 3),
(5, 6),(5, 7),
(6, 1),(6, 2),(6, 3),(6, 4),(6, 5),(6, 6),(6, 7),
(7, 2);





-- 그냥 실행 가능
insert into solafy.rank(uid)
values
("DFEIJC23WOSKXCNSWQ"),
("DF112C23WOSKXCNSWQ"),
("DFEIJNGRW2SKXCNSWQ"),
("DFEIJHRDX8SKXCNSWQ"),
("DFEIJJHGT7SKXCNSWQ");
update solafy.rank set 
solProblem = 100, solByCategoryProblem = 101, registerProblem = 102, adoptProblemSol = 103
where uid = 'DFEIJC23WOSKXCNSWQ';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 101, registerProblem = 5102, adoptProblemSol = 122
where uid = 'DF112C23WOSKXCNSWQ';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 222, registerProblem = 1102, adoptProblemSol = 155
where uid = 'DFEIJNGRW2SKXCNSWQ';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 333, registerProblem = 2102, adoptProblemSol = 166
where uid = 'DFEIJHRDX8SKXCNSWQ';

update solafy.rank set 
solProblem = 100, solByCategoryProblem = 444, registerProblem = 3102, adoptProblemSol = 177
where uid = 'DFEIJJHGT7SKXCNSWQ';


-- answerModifyboard 있어야함
insert into solafy.answermodifyreply(replyNo,articleNo,contents,uid)
values
(1,1,"VXUE가 아니고 Vuex입니다만? 수정좀 부탁", "DFEIJC23WOSKXCNSWQ"),
(2,1,"답 틀린거 같습니다. 10이 답입니다.", "DF112C23WOSKXCNSWQ"),
(3,1,"주사위를 굴려 91이 나왔습니다. 답은 91입니다.", "DFEIJNGRW2SKXCNSWQ"),
(4,2,"연필을 굴렸습니다. 5가 나왔습니다. 답은 5입니다.", "DFEIJHRDX8SKXCNSWQ"),
(5,3,"답 : 27261", "DFEIJJHGT7SKXCNSWQ"),
(6,3,"연필을 굴렸습니다. 12345가 나왔습니다. 답은 12345입니다.", "DFEIJHRDX8SKXCNSWQ"),
(7,2,"답 : 1234", "DFEIJJHGT7SKXCNSWQ"),
(8,5,"답 틀린거 같습니다. 10이 답입니다.", "DF112C23WOSKXCNSWQ"),
(9,4,"4번 문제의 답안을 수정해야 합니다.", "DFEIJNGRW2SKXCNSWQ"),
(10,5,"5번 문제 답안 수정 부탁드려요", "DF112C23WOSKXCNSWQ");

-- freeBoard 정보 있어야함
insert into solafy.freereply(replyNo,articleNo,contents,uid)
values
(1,1, "나는 견2", "DFEIJC23WOSKXCNSWQ"),
(2,1, "나는 호랑돌이!", "DF112C23WOSKXCNSWQ"),
(3,2, "나는 쥬희!", "DFEIJNGRW2SKXCNSWQ"),
(4,2, "나는 냥집사 야옹!", "DFEIJHRDX8SKXCNSWQ"),
(5,2, "나는 상우좌아~", "DFEIJJHGT7SKXCNSWQ"),
(6,1, "나는 견222222222222", "DFEIJC23WOSKXCNSWQ"),
(7,1, "나는 호랑돌22222222!", "DF112C23WOSKXCNSWQ"),
(8,1, "나는 쥬희hhhhhhhhhhhhh!", "DFEIJNGRW2SKXCNSWQ"),
(9,1, "나는 냥집사 야옹oooooooooooooooooooo!", "DFEIJHRDX8SKXCNSWQ"),
(10,1, "나는 상우!!!!!!!!!!!!!!!!!!", "DFEIJJHGT7SKXCNSWQ");