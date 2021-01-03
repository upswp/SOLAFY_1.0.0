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
      <q-card-section align="right"
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
      articleNo: this.$route.params.articleNo
    };
  },
  methods: {
    // 게시글 수정
    updateArticle: function() {
      // 오류 상태 저장 플래그
      var successFlag = true;

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
    }
  },

  // 기존의 정보를 입력칸에 뿌려주기 위한 작업
  created() {
    // 해당 게시판에서,
    // 게시글 번호를 통해,
    // 게시글 정보 가져옴
    Axios.get(
      `/${this.$store.state.boardType}/selectArticleByArticleNo/${this.articleNo}`
    )
      .then(response => {
        // 가져왔다면 게시글 정보를 data에 기억
        this.article = response.data;
      })
      .catch(() => {
        this.errored = true;
      })

      .finally(() => (this.loading = false));
  }
};
</script>
