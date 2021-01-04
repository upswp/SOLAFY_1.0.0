<template>
  <!-- 게시글 작성 페이지 시작 -->
  <q-page class="q-pa-md">
    <q-card flat bordered>
      <q-item>
        <q-item-section> <b>게시글 작성</b> </q-item-section>
      </q-item>
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <img :src="profileImageUrl" />
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
          <q-item-label caption> 작성자 : {{ article.nickname }} </q-item-label>
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
                @keyup.enter="selectProblemByKeyword"
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
                  <q-icon name="search" @click="selectProblemByKeyword" />
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
          {{ problemInfo.problemNo }}번 문제<br />
          <span style="font-size:0.5em"
            >문제 작성자 : {{ problemInfo.nickname }}</span
          ><br />
          <b>{{ problemInfo.contents }}</b
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
        <q-btn color="red" label="취소" @click="goToBoardList" />
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
      loading: false,
      // 프로필 사진 주소값 저장 변수
      profileImageUrl: "",
      // 게시판 형식 값을 store에서 가져와 저장하는 변수
      boardType: this.$store.state.boardType,
      // 문제 검색 테이블 안내 문구 변수
      message: "",
      article: {
        uid: SessionStorage.getItem("loginUser").uid,
        nickname: SessionStorage.getItem("loginUser").nickname,
        problemNo: "",
        uid_submitter: "",
        title: "",
        contents: "",
        isNotice: false,
        groupNo: 1
      },

      // * 문제검색을 위한 데이터 시작
      // 문제 선택 여부 flag
      isProblemSelected: false,
      // 문제 검색 옵션 선택 저장 변수
      selection: "제목",
      // 문제 검색 키워드 변수
      keyword: "",
      // 문제 검색 옵션 배열
      options: ["제목", "문제번호", "작성자"],
      // 문제 검색 테이블에 표시할 문제 모음
      problems: [],
      // 선택한 문제 정보 저장 변수
      problemInfo: [],
      // 문제 검색용 테이블 컬럼
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
      ]
      // * 문제 검색을 위한 데이터 끝
    };
  },
  methods: {
    /**
     * @Method설명 : 게시글 작성 요청 메소드
     * @변경이력 :
     */
    createArticle: function() {
      var successFlag = true;

      // 게시글 제목이 비어 있을 때
      if (this.article.title === null || this.article.title == "") {
        this.$q.notify({
          message: "*제목* 비어 있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }

      // 게시글 내용이 비어 있을 때
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          message: "*내용* 비어 있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }

      // 제목과 내용이 채워져 있을 때
      if (successFlag) {
        // 입력한 값을 묶어서 던진다
        Axios.post(`/${this.boardType}/createArticle`, this.article)
          .then(response => {
            if (response.data === "success") {
              // 목록으로 돌아감
              this.goToBoardList();
              // 등록 성공알림
              this.$q.notify({
                message: "게시물 등록 성공",
                color: "green"
              });
            } else {
              // 게시물 등록 실패
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

    /**
     * @Method설명 : 검색 옵션에 해당하는 문제 검색 및 검색 예외처리 메소드
     * @변경이력 :
     */
    selectProblemByKeyword: function() {
      this.loading = true;
      // 검색 옵션과 검색 키워드에 맞는 문제를 얻어 옴
      Axios.get(`/problem/search/${this.selection}/${this.keyword}`)
        .then(response => {
          // 반환된 문제 배열을 problems에 저장한다
          this.problems = response.data;
          // 반환된 문제 배열의 크기가 0일 때
          if (this.problems.length === 0) {
            this.$q.notify({
              color: "red-6",
              textColor: "white",
              icon: "warning",
              message: "조회 실패"
            });
          }
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

    /**
     * @Method설명 : 선택한 문제 번호에 해당하는 문제를 가져오는 메소드
     * @변경이력 :
     */
    selectProblem: function(evt, row) {
      Axios.get(`problem/${row.problemNo}`)
        .then(response => {
          // 반환된 값을 problemInfo에 저장한다
          this.problemInfo = response.data["problem"];
          // 선택지가 존재하는 문제일 때
          if (this.problemInfo.multipleChoice !== null)
            // 콤마로 구분되어 있는 선택지를 multipleChoice에 저장
            this.multipleChoice = this.problemInfo.multipleChoice.split(",");

          // 게시글에 필요한 문제번호, 문제 제출자 정보를 저장
          this.article.problemNo = this.problemInfo.problemNo;
          this.article.uid_submitter = this.problemInfo.uid;
        })
        .catch(error => console.log(error))
        .finally(() => (this.loading = false));
      this.isProblemSelected = true;
    },

    /**
     * @Method설명 : 로그인된 사용자의 프로필 이미지를 가져오는 메소드
     * @변경이력 :
     */
    getProfileImageUrl: function(uid) {
      firebaseSt
        .ref()
        .child("profileimg/" + uid)
        .getDownloadURL()
        .then(url => {
          this.profileImageUrl = url;
        })
        .catch(function(error) {
          console.log("error is ", error);
        });
    },

    /**
     * @Method설명 : 문제 검색 테이블의 헤더에 들어갈 안내문 선택 메소드
     * @변경이력 :
     */
    setMessage: function() {
      if (this.boardType === "answermodify") {
        this.message = "수정을 요청할 문제 검색";
      } else if (this.boardType === "question") {
        this.message = "질문할 문제 검색";
      }
    },

    /**
     * @Method설명 : 게시판 목록으로 돌아가는 메소드
     * @변경이력 :
     */
    goToBoardList: function() {
      this.$router.push({
        name: `${this.boardType}-board-list`
      });
    }
  },
  created() {
    this.setMessage();
    this.getProfileImageUrl(SessionStorage.getItem("loginUser").uid);
  }
};
</script>
