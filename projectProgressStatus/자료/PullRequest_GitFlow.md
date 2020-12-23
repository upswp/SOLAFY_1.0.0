# Github 활용법

## 1. Pull requests

![image-20201223194307640](C:\Users\lay07\AppData\Roaming\Typora\typora-user-images\image-20201223194307640.png)

- 병합하기 전에 다른 개발자들에게 리뷰&컨펌을 받고 싶어요! 

  -> Pull requests(병합 요청)를 보내세요!!

<img src="C:\Users\lay07\AppData\Roaming\Typora\typora-user-images\image-20201223202518904.png" alt="image-20201223202518904" style="zoom:50%;" />

: 코드 변경사항에 댓글을 달아 리뷰가능

-> 피드백을 받은뒤 approve를 받음

-> 밑에 'Squash and merge'를 누름

[출처 : https://milooy.wordpress.com/2017/06/21/working-together-with-github-tutorial/]



## 2. Git-flow

: git 방법론

-> 5가지의 브랜치를 사용

* **master** : 기준이 되는 브랜치로 제품을 배포하는 브랜치

* **develop** : 개발 브랜치로 개발자들이 이 브랜치를 기준으로 각자 작업한 기능들을 Merge

* **feature** : 단위 기능을 개발하는 브랜치로 기능 개발이 완료되면 develop 브랜치에 합침

* **release** : 배포를 위해 master 브랜치로 보내기 전에 먼저 QA(품질검사)를 하기위한 브랜치 

* **hotfix** : master 브랜치로 배포를 했는데 버그가 생겼을 떄 긴급 수정하는 브랜치

<img src="https://t1.daumcdn.net/cfile/tistory/99CD994C5E69CCF223" alt="Vincent Driessen의 블로그 참조" style="zoom:50%;" />

[Git-flow 순서]

1. master branch에서 시작

2. 동일한 branch를 develop에도 생성(개발자들이 사용하게 되는 branch)

3. 개발을 진행할 때 기능 구현이 필요한 경우

   * A개발자 -> develop branch에서 feature branch를 하나 생성 후 A기능 구현
   * B개발자 -> develop branch에서 feature branch를 하나 생성 후 B기능 구현

4. 완료된 feature branch를 검토 한 후 develop브랜치에 합침(Merge)

5. 모든 기능이 완료되면 develop branch를 release branch로 생성

   -> QA를 하며 보완점을 보완하고 버그를 픽스

6. 모든 것이 완료되면 release branch를 master&develop branch로 보냄

   `*master branch에서 버젼 추가를 위해 태그를 하나 생성하고 배포`

7. 배포 후, 미처 발견하지 못한 버그가 있을 경우 hotfixes branch를 만들어 긴급 수정 후 태그를 생성하고 수정 배포

[출처 : https://uxgjs.tistory.com/183]

## 3. Github GUI Tool - SourceTree

<img src="C:\Users\lay07\AppData\Roaming\Typora\typora-user-images\image-20201223205628164.png" alt="image-20201223205628164" style="zoom: 80%;" />