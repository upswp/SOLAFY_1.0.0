<template>
  <!-- 게시글 수정페이지 시작 -->
  <q-page class="q-pa-md">
    <q-card flat bordered>
      <q-item>
        <q-item-section avatar>
          <!-- 본인이 수정할테니까 현 사용자의 프로필 사진 가져온다 -->
          <q-avatar>
            <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <!-- 게시글 번호를 불러온다(표시용) -->
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

          <!-- 닉네임 정보와 게시글 작성시간(표시용) -->
          <q-item-label caption>
            닉네임 : {{ article.nickname }} <br />
            작성시간 : {{ article.regiTime }}
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-separator />

      <q-btn
        color="primary"
        label="문제 변경하기"
        @click="isProblemSelected = false"
      />
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
      <q-card-section align="right" v-if="this.boardType === `free`"
        ><q-checkbox v-model="article.notice" label="공지사항 여부" />
      </q-card-section>
      <q-separator />
      <!-- 공지사항 여부 끝 -->

      <!-- 수정/취소 버튼 시작 -->
      <q-card-actions align="right">
        <q-btn color="primary" label="글 수정하기" @click="updateArticle" />
        <q-btn color="red" label="취소" @click="goToDetail" />
      </q-card-actions>
      <!-- 수정/취소 버튼 끝 -->
    </q-card>
  </q-page>
  <!-- 글 수정하기 끝 -->
</template>
<script>
import Axios from "axios";
import { mapState } from "vuex";
export default {
  data() {
    return {
      // 이미 있던 내용을 수정하므로, 기존 데이터를 저장할 변수 선언
      article: [],
      articleNo: this.$route.params.articleNo,
      boardType: this.$store.state.boardType,

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
      isProblemSelected: true,
      problemInfo: [],
      //문제 검색을 위한 데이터 끝

      loading: "",
      message: ""
    };
  },
  methods: {
    // 게시글 수정
    updateArticle: function() {
      // 오류 상태 저장 플래그
      var successFlag = true;
      console.log("finally", this.article);

      // 제목 비어있는지 확인
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
      // 내용 비어있는지 확인
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

      // 제목/내용이 비어있지 않다면
      if (successFlag) {
        var flag = false;
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
                  this.goToDetail();
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

    // 게시글 번호를 parameter로 기억하며 해당글 상세페이지로 날아간다
    goToDetail: function() {
      this.$router.push({
        name: `${this.$store.state.boardType}-board-detail`,
        params: { articleNo: this.articleNo }
      });
    },

    // 문제 검색을 위한 메서드 시작
    //문제 검색 및 검색 예외처리
    search: function() {
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
    // 문제를 선택했을때 메서드
    selectProblemByNo: function() {
      console.log("hwe4eywey");
      console.log(this.article.problemNo, "is not good");
      Axios.get(`problem/${this.article.problemNo}`)
        .then(response => {
          console.log("hello----------");
          console.log("response.data", response.data);
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
    }
  },

  // 기존의 정보를 입력칸에 뿌려주기 위한 작업
  mounted() {
    // 해당 게시판에서,
    // 게시글 번호를 통해,
    // 게시글 정보 가져옴
    Axios.get(
      `/${this.$store.state.boardType}/selectArticleByArticleNo/${this.articleNo}`
    )
      .then(response => {
        console.log(this.articleNo);
        // 가져왔다면 게시글 정보를 data에 기억
        this.article = response.data;
        console.log(this.article);
        console.log("hello?");
        console.log("hello?");
        this.selectProblemByNo();
      })
      .catch(() => {
        this.errored = true;
      })

      .finally(() => (this.loading = false));
  }
};
</script>
