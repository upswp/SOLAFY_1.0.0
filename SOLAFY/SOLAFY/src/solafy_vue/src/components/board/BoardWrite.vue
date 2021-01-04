<template>
  <!-- 게시글 작성 페이지 시작 -->
  <q-page class="q-pa-md">
    <q-card flat bordered>
      <q-item>
        <q-item-section> 게시글 작성 </q-item-section>
      </q-item>
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <!-- TODO: 프로필사진 가져오기 -->
            <!-- <img src="https://cdn.quasar.dev/img/boy-avatar.png" /> -->
            <img id="profileImg" />
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <q-item-label>
            <!-- 제목 입력 부분 -->
            <q-input
              v-model="article.title"
              label="제목"
              stack-label
              aria-placeholder="제목을 입력해주세요"
              dense
          /></q-item-label>
          <!-- TODO: 닉네임은 자동으로 불러옴 -->
          <q-item-label caption>
            {{ article.uid }}
            {{ article.nickname }}
            닉네임 : 파베에서 로그인 정보 가져오기
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-separator />

      <!-- 게시글에서 다룰 문제 검색 부분 구현 시작(답안수정게시판, 질문게시판 한정) -->
      <template
        v-if="
          this.boardType === `answermodify` || this.boardType === `question`
        "
      >
        <!-- 문제를 선택하지 않았을 경우 -->
        <q-card-section v-if="!isProblemSelected">
          <q-table
            :title="message"
            :data="problems"
            :columns="columns"
            row-key="name"
            :loading="loading"
            @row-click="selectProblem"
          >
            <!-- 검색 조건, 키워드에 따른 문제 검색 시작 -->
            <template v-slot:top-right>
              <q-input
                bottom-slots
                v-model="keyword"
                label="검색어를 입력해주세요"
                counter
                maxlength="12"
                @keyup.enter="search"
                style="min-width:400px"
              >
                <template v-slot:before>
                  <q-select
                    id="selectbox"
                    filled
                    v-model="selection"
                    :options="options"
                    label="검색조건"
                    style="min-width:120px"
                  />
                </template>
                <template v-slot:append>
                  <q-icon
                    v-if="keyword !== ''"
                    name="close"
                    @click="keyword = ''"
                    class="cursor-pointer"
                  />
                  <q-icon name="search" @click="search" />
                </template>
              </q-input>
            </template>
            <!-- 검색 조건, 키워드에 따른 문제 검색 끝 -->

            <!-- 데이터가 존재하지 않는다면 안내문구 출력 시작 -->
            <template v-slot:no-data>
              <div class="full-width row flex-center text-accent q-gutter-sm">
                <q-icon size="2em" name="volume_off" />
                <b style="font-size:15px">
                  문제를 검색해주세요
                </b>
              </div>
            </template>
            <!-- 데이터가 존재하지 않는다면 안내문구 출력 끝 -->
          </q-table>
        </q-card-section>
        <!-- 문제가 선택 되었을 경우 -->
        <q-card-section v-else-if="isProblemSelected">
          <!-- 문제 정보 표시 시작 -->
          <q-separator />
          문제 작성자 : {{ problemInfo.nickname }}<br /><br />
          {{ problemInfo.problemNo }}번째 문제 내용<br />
          <strong>{{ problemInfo.contents }}</strong
          ><br />

          <template v-if="problemInfo.type === 0">
            <div v-for="(choice, index) in multipleChoice" :key="index">
              {{ index + 1 }} : {{ choice }}
            </div>
          </template>
          <!-- 검색 초기화 버튼 시작 -->
          <template>
            <q-btn
              outline
              color="black"
              label="문제 다시 선택하기"
              flat
              @click="isProblemSelected = false"
            />
          </template>
          <!-- 검색 초기화 버튼 끝 -->
        </q-card-section>
        <!-- 문제 정보 표시 끝 -->
      </template>
      <!-- 게시글에서 다룰 문제 검색 부분 구현 끝 -->
      <q-separator />
      <q-card-section>
        <q-card-section class="col-4">
          <!-- 내용 입력부분 -->
          <q-input
            v-model="article.contents"
            filled
            type="textarea"
            label="내용을 입력해주세요"
            autogrow
          />
          <!-- 내용 입력 부분 끝 -->
        </q-card-section>
      </q-card-section>
      <!-- TODO: 공지사항 사용하는 게시판 한정, 관리자 권한의 인원만 사용하도록 함 -->
      <q-card-section align="right" v-if="this.boardType === `free`"
        ><q-checkbox v-model="article.notice" label="공지사항 여부" />
      </q-card-section>
      <q-separator />
      <q-card-actions align="right">
        <q-btn color="primary" label="글 등록" @click="createArticle" />
        <q-btn color="red" label="취소" @click="goToBoard" />
      </q-card-actions>
    </q-card>
  </q-page>
  <!-- 글 등록 form 끝 -->
</template>
<script>
import Axios from "axios";

import { firebaseAuth, firebaseSt } from "boot/firebase";
import { SessionStorage, uid } from "quasar";
import { mapState } from "vuex";

export default {
  data() {
    return {
      // 입력내용이 저장되는 값들
      article: {
        //추가 정보
        // TODO: 현재의 UID를 가져와야한다. 힘들면 nickname이라도
        // uid: firebaseAuth.currentUser.uid,
        uid: SessionStorage.getItem("loginUser").uid,
        nickname: SessionStorage.getItem("loginUser").nickname,
        problemNo: "",
        uid_submitter: "",

        //기존 자유게시판 정보
        title: "",
        contents: "",
        isNotice: false,
        groupNo: 1
      },
      profileUrl: "https://cdn.quasar.dev/img/boy-avatar.png",
      // 게시판 형식 값을
      // store에서 가져와 data에 저장
      boardType: this.$store.state.boardType,

      loading: false,
      message: "",
      // 문제검색을 위한 데이터 시작
      selection: "제목",
      options: ["제목", "문제번호", "작성자"],
      keyword: "",
      columns: [
        {
          name: "problemNo",
          required: true,
          label: "문제번호",
          align: "left",
          field: row => row.problemNo,
          format: val => `${val}`,
          sortable: true,
          style: "width = 10px"
        },
        {
          name: "nickname",
          label: "작성자",
          required: true,
          align: "left",
          field: row => row.nickname,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "title",
          label: "제목",
          required: true,
          align: "left",
          field: row => row.title,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "regiTime",
          label: "등록일자",
          required: true,
          align: "left",
          field: row => row.regiTime,
          format: val => `${val}`,
          sortable: true
        }
      ],
      problems: [],
      isProblemSelected: false,
      problemInfo: []
      //문제 검색을 위한 데이터 끝
    };
  },
  methods: {
    // 게시글 생성
    createArticle: function() {
      var successFlag = true;
      this.article.uid = "DFEIJC23WOSKXCNSWQ";
      this.article.uid_submitter = this.problemInfo.uid;
      console.log(this.article.uid_submitter);

      // 게시글 제목이 비어있다면
      if (this.article.title === null || this.article.title == "") {
        this.$q.notify({
          message: "*제목* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }

      // 게시글 내용이 비어있다면
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          message: "*내용* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }

      // 제목과 내용이 비어있지 않다면!
      if (successFlag) {
        // 입력한 값을 묶어서 던진다
        Axios.post(`/${this.boardType}/createArticle`, this.article)
          .then(response => {
            if (response.data === "success") {
              // 목록으로 돌아감
              this.goToBoard();
              // 등록 성공알림
              this.$q.notify({
                message: "게시물 등록 성공",
                color: "green"
              });
            } else {
              // 실패
              this.$q.notify({
                message: "게시물 등록 실패",
                color: "red"
              });
            }
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      } else {
        return;
      }
    },

    // 목록으로 돌아가는 메서드.
    // 해당 게시판에 해당하는 리스트로 날아간다
    goToBoard: function() {
      this.$router.push({
        name: `${this.boardType}-board-list`
      });
    },

    // 문제 검색을 위한 메서드 시작
    //문제 검색 및 검색 예외처리
    search: function() {
      // TODO: 로그인 정보 불러오기 점검
      console.log(typeof this.article.uid, "hello");
      console.log(firebaseAuth.currentUser.uid);
      console.log(firebaseAuth.currentUser.nickname);
      this.loading = true;
      Axios.get(`/problem/search/${this.selection}/${this.keyword}`)
        .then(response => {
          this.problems = response.data;
          if (this.problems.length === 0) {
            this.$q.notify({
              color: "red-6",
              textColor: "white",
              icon: "warning",
              message: "조회 실패"
            });
          }
          console.log(this.problems[0]);
        })
        .catch(() => {
          this.$q.notify({
            color: "red-6",
            textColor: "white",
            icon: "warning",
            message: "조회 실패"
          });
        })
        .finally(() => (this.loading = false));
    },
    // 문제를 선택했을때 메서드
    selectProblem: function(evt, row) {
      Axios.get(`problem/${row.problemNo}`)
        .then(response => {
          this.problemInfo = response.data["problem"];
          console.log(this.problemInfo);
          if (this.problemInfo.multipleChoice !== null)
            this.multipleChoice = this.problemInfo.multipleChoice.split(",");
          this.article.problemNo = this.problemInfo.problemNo;
          this.article.uid_submitter = this.problemInfo.uid;
          console.log(this.article.problemNo);
        })
        .catch(error => console.log(error))
        .finally(() => (this.loading = false));
      this.isProblemSelected = true;
    },
    getProfileImageUrl: function() {
      var returnValue;
      var hello = firebaseSt
        .ref()
        .child("profileimg/" + this.article.uid)
        .getDownloadURL()
        .then(function(url) {
          console.log(url);
          document.getElementById("profileImg").setAttribute("src", url);
          return url;
          // console.log(this.profileUrl);
          // Get the download URL for 'images/stars.jpg'
          // This can be inserted into an <img> tag
          // This can also be downloaded directly
        })
        .catch(function(error) {
          // Handle any errors
          console.log("error is ", error);
        });
      console.log(hello, "this is hello");
      hello.then(function(result) {
        returnValue = hello;
      });
      return returnValue;
    }
  },
  // 게시판에 따른 안내문구 설정
  mounted() {
    if (this.boardType === "answermodify") {
      this.message = "수정을 요청할 문제 검색";
    } else if (this.boardType === "question") {
      this.message = "질문할 문제 검색";
    }
    console.log("hello");
    // this.profileUrl = firebaseSt.ref().child("profileimg/" + this.article.uid);
    this.profileUrl = this.getProfileImageUrl();
    console.log(this.getProfileImageUrl(), "why");
  }
};
</script>
