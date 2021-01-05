<template>
  <!-- 게시글 수정페이지 시작 -->
  <q-page class="q-pa-md">
    <q-card flat bordered>
      <q-item>
        <q-item-section avatar>
          <!-- 글을 수정하는 본인의 프로필 사진 표시 -->
          <q-avatar>
            <img :src="profileImageUrl" />
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <!-- 게시글 번호 표시 -->
          <q-item-label overline> #{{ article.articleNo }} </q-item-label>
          <q-item-label>
            <!-- 수정할 제목 입력 시작 -->
            <q-input
              v-model="article.title"
              label="제목"
              stack-label
              aria-placeholder="제목을 입력해주세요"
              dense
          /></q-item-label>
          <!-- 수정할 제목 입력 끝 -->

          <!-- 닉네임 정보와 게시글 작성시간 표시 -->
          <q-item-label caption>
            닉네임 : {{ article.nickname }} <br />
            작성시간 : {{ article.regiTime }}
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
          <q-btn
            outline
            color="white"
            label="기존 문제 선택"
            flat
            @click="isProblemSelected = true"
          />
          <q-table
            :title="message"
            :data="problems"
            :columns="columns"
            row-key="name"
            :loading="loading"
            @row-click="requestSelectProblem"
          >
            <!-- 검색 조건, 키워드에 따른 문제 검색 부분 시작 -->
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
            <!-- 검색 조건, 키워드에 따른 문제 검색 부분 끝 -->

            <!-- 데이터가 존재하지 않을 때 안내문구 출력 시작 -->
            <template v-slot:no-data>
              <div class="full-width row flex-center text-accent q-gutter-sm">
                <q-icon size="2em" name="volume_off" />
                <b style="font-size:15px">
                  문제를 검색해주세요
                </b>
              </div>
            </template>
            <!-- 데이터가 존재하지 않을 때 안내문구 출력 끝 -->
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

      <q-card-section>
        <q-card-section class="col-4">
          <!-- 수정할 내용 입력 시작 -->
          <q-input
            v-model="article.contents"
            filled
            type="textarea"
            aria-placeholder="내용을 입력해주세요"
            autogrow
          />
          <!-- 수정할 내용 입력 끝 -->
        </q-card-section>
      </q-card-section>

      <!-- 공지사항 여부 시작(자유게시판 한정) -->

      <q-card-section
        align="right"
        v-if="this.boardType === `free` && isQualified"
        ><q-checkbox v-model="article.notice" label="공지사항 여부" />
      </q-card-section>
      <q-separator />
      <!-- 공지사항 여부 끝 -->

      <!-- 수정/취소 버튼 시작 -->
      <q-card-actions align="right">
        <q-btn color="primary" label="글 수정하기" @click="updateArticle" />
        <q-btn color="red" label="취소" @click="goToBoardDetail" />
      </q-card-actions>
      <!-- 수정/취소 버튼 끝 -->
    </q-card>
  </q-page>
  <!-- 글 수정하기 끝 -->
</template>
<script>
import Axios from "axios";
import { firebaseAuth, firebaseSt } from "boot/firebase";
import { SessionStorage, uid } from "quasar";
import { mapState } from "vuex";
export default {
  data() {
    return {
      loading: "",

      // 문제 검색 테이블 안내 문구 변수
      message: "",

      // 기존 게시글 정보 저장 배열
      article: [],
      articleNo: this.$route.params.articleNo,
      boardType: this.$store.state.boardType,

      // 프로필 사진 주소값 저장 변수
      profileImageUrl: "",
      // 로그인한 회원의 공지사항 생성 가능 여부 flag
      isQualified: false,
      // 문제 선택 여부 flag
      isProblemSelected: true,
      // 문제 검색 테이블에 표시할 문제 모음
      problems: [],
      // 선택한 문제 정보 저장 변수
      problemInfo: [],
      // 문제 검색 옵션 선택 저장 변수
      selection: "제목",
      // 문제 검색 키워드 변수
      keyword: "",
      // 문제 검색 옵션 배열
      options: ["제목", "문제번호", "작성자"],
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
      //문제 검색을 위한 데이터 끝
    };
  },
  methods: {
    /**
     * @Method설명 : 게시글 수정 요청 메서드
     * @변경이력 :
     */
    updateArticle: function() {
      // 오류 상태 저장 플래그
      var successFlag = true;

      // 수정한 제목이 비어 있을 때
      if (this.article.title === null || this.article.title == "") {
        this.$q.notify({
          timeout: 1,
          progress: true,
          position: "center",
          message: "*제목* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }
      // 수정한 내용이 비어 있을 때
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          timeout: 1,
          progress: true,
          position: "center",
          message: "*내용* 비어있으면 안돼요!",
          color: "oragne"
        });
        successFlag = false;
      }

      // 제목과 내용이 채워져 있을 때
      if (successFlag) {
        // 수정 여부 재 확인
        this.$q
          .dialog({
            title: "Confirm",
            message: "정말 수정하시겠습니까?",
            cancel: true,
            persistent: true
          })
          // OK하면 수정요청 보냄
          .onOk(() => {
            Axios.post(
              `/${this.$store.state.boardType}/updateArticle`,
              this.article
            )
              .then(response => {
                if (response.data === "success") {
                  this.$q.notify({
                    progress: true,
                    message: "게시물 수정 성공",
                    color: "green"
                  });
                  // 게시글 상세 페이지로 복귀
                  this.goToBoardDetail();
                } else {
                  this.$q.notify({
                    progress: true,
                    message: "게시물 수정 실패",
                    color: "red"
                  });
                }
              })
              .catch(() => {
                this.errored = true;
              })
              .finally(() => (this.loading = false));
          })
          // 재차확인에서 취소하거나 무시하면 수정화면으로 복귀
          .onCancel(() => {
            return;
          })
          .onDismiss(() => {
            return;
          });
      } else {
        return;
      }
    },

    /**
     * @Method설명 : 기존의 게시글 정보를 불러오는 메소드
     * @변경이력 :
     */
    selectArticleByArticleNo: function() {
      Axios.get(
        `/${this.$store.state.boardType}/selectArticleByArticleNo/${this.articleNo}`
      )
        .then(response => {
          // 반환 된 게시글 정보를 data에 기억
          this.article = response.data;
          this.selectProblem(this.article.problemNo);
        })
        .catch(() => {
          this.errored = true;
        })

        .finally(() => (this.loading = false));
    },

    /**
     * @Method설명 : 문제 검색 테이블에서 선택한 행의 문제번호를
     *               selectProblem에 전해주는 메소드
     * @변경이력 :
     */
    requestSelectProblem: function(evt, row) {
      this.selectProblem(row.problemNo);
    },

    /**
     * @Method설명 : 검색 옵션에 해당하는 문제 검색 및 검색 예외처리 메소드
     * @변경이력 :
     */
    selectProblemByKeyword: function() {
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
    selectProblem: function(problemNo) {
      Axios.get(`problem/` + problemNo)
        .then(response => {
          this.problemInfo = response.data["problem"];
          if (this.problemInfo.multipleChoice !== null)
            this.multipleChoice = this.problemInfo.multipleChoice.split(",");
          this.article.problemNo = this.problemInfo.problemNo;
          this.article.uid_submitter = this.problemInfo.uid;
        })
        .catch(error => console.log(error))
        .finally(() => (this.loading = false));
      this.isProblemSelected = true;
    },

    /**
     * @Method설명 : uid에 해당하는 사용자의 프로필 이미지를 가져오는 메소드
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
        .catch(error => {
          this.profileImageUrl = "https://cdn.quasar.dev/img/boy-avatar.png";
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
     * @Method설명 : 현재 로그인 사용자가 관리자 권한이 있는지 판단
     * @변경이력 :
     */
    setIsQualified: function() {
      var loginUserUid = SessionStorage.getItem("loginUser").uid;
      Axios.get(`user/selectbyuid/${loginUserUid}`)
        .then(response => {
          if (response.data.admin === 0) this.isQualified = true;
        })
        .catch(error => console.log(error))
        .finally(() => {
          this.loading = true;
        });
    },

    /**
     * @Method설명 : 해당 글 상세페이지로 이동하는 메소드.
     *                (parameter로 articleNo를 전달)
     * @변경이력 :
     */
    goToBoardDetail: function() {
      this.$router.push({
        name: `${this.$store.state.boardType}-board-detail`,
        params: { articleNo: this.articleNo }
      });
    }
  },

  created() {
    this.selectArticleByArticleNo();
    this.setMessage();
    this.getProfileImageUrl(SessionStorage.getItem("loginUser").uid);
    this.setIsQualified();
  }
};
</script>
