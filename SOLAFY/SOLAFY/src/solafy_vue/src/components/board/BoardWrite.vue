<template>
  <!-- 게시글 작성 페이지 시작 -->
  <q-page class="q-pa-md">
    <q-card flat bordered>
      <q-item>
        <q-item-section>
          게시글 작성
        </q-item-section>
      </q-item>
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <!-- TODO: 프로필사진 가져오기 -->
            <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
          </q-avatar>
        </q-item-section>

        <!-- 찐 입력페이지 시작 -->
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
            닉네임 : 파베에서 로그인 정보 가져오기
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-separator />
      <q-card-section>
        <q-card-section class="col-4">
          <!-- 내용 입력부분 -->
          <q-input
            v-model="article.contents"
            filled
            type="textarea"
            label="내용"
            aria-placeholder="내용을 입력해주세요"
            autogrow
          />
        </q-card-section>
      </q-card-section>
      <!-- TODO: 공지사항 사용하는 게시판 한정, 관리자 권한의 인원만 사용하도록 함 -->
      <q-card-section align="right"
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
export default {
  data() {
    return {
      // 입력내용이 저장되는 값들
      article: {
        title: "",
        contents: "",
        isNotice: false,
        groupNo: 1
      },
      // 게시판 형식 값을
      // store에서 가져와 data에 저장
      boardType: this.$store.state.boardType
    };
  },
  methods: {
    // 게시글 생성
    createArticle: function() {
      var successFlag = true;

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
        // TODO: 현재의 UID를 가져와야한다. 힘들면 nickname이라도
        this.article.uid = "DFEIJC23WOSKXCNSWQ";
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
    }
  }
};
</script>
