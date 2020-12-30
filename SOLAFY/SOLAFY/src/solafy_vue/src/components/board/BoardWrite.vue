<template>
  <q-page class="q-pa-md">
    <!-- final card 시작 -->
    <q-card flat bordered>
      <q-item>
        <q-item-section>
          게시글 작성
        </q-item-section>
      </q-item>
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <q-item-label>
            <q-input
              v-model="article.title"
              label="제목"
              stack-label
              aria-placeholder="제목을 입력해주세요"
              dense
          /></q-item-label>
          <q-item-label caption>
            닉네임 : 파베에서 로그인 정보 가져오기
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-separator />
      <q-card-section>
        <q-card-section class="col-4">
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
      <q-card-section align="right"
        ><q-checkbox v-model="article.notice" label="공지사항 여부" />
      </q-card-section>
      <q-separator />
      <q-card-actions align="right">
        <q-btn color="primary" label="글 등록" @click="createArticle" />
        <q-btn color="red" label="취소" @click="goToFreeBoard" />
      </q-card-actions>
    </q-card>
  </q-page>
  <!-- 글 등록 form 끝 -->
</template>
<script>
export default {
  methods: {
    createArticle: function() {
      var successFlag = true;
      if (this.article.title === null || this.article.title == "") {
        this.$q.notify({
          message: "*제목* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }
      if (this.article.contents == null || this.article.contents == "") {
        this.$q.notify({
          message: "*내용* 비어있으면 안돼요!",
          color: "orange"
        });
        successFlag = false;
      }
      if (successFlag) {
        this.article.uid = "DFEIJC23WOSKXCNSWQ";
        Axios.post(`/free/createArticle`, this.article)
          .then(response => {
            if (response.data === "success") {
              this.showFlag = "list";
              this.showList();
              this.resetForm();
              this.$q.notify({
                message: "게시물 등록 성공",
                color: "green"
              });
            } else {
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
    // 입력 form 초기화는 title, contents, isNotice에 적용된다
    resetForm: function() {
      this.article.title = null;
      this.article.contents = null;
      this.article.isNotice = false;
    }
  }
};
</script>
