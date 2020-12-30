<template>
  <q-page class="q-pa-md">
    <!-- final card 시작 -->
    <q-card flat bordered>
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <q-item-label overline> #{{ article.articleNo }} </q-item-label>
          <q-item-label>
            <q-input
              v-model="article.title"
              label="제목"
              stack-label
              aria-placeholder="제목을 입력해주세요"
              dense
          /></q-item-label>
          <q-item-label caption>
            닉네임 : {{ article.nickname }} <br />
            작성시간 : {{ article.regiTime }}
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
        <q-btn color="primary" label="글 수정하기" @click="updateArticle" />
        <q-btn color="red" label="취소" @click="goToFreeBoard" />
      </q-card-actions>
    </q-card>
  </q-page>
  <!-- 글 수정하기 끝 -->
</template>
<script>
export default {
  updateArticle: function() {
    var successFlag = true;
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
    if (successFlag) {
      console.log("you are here");
      var flag = false;
      this.$q
        .dialog({
          title: "Confirm",
          message: "정말 수정하시겠습니까?",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          Axios.post(`/free/updateArticle`, this.article)
            .then(response => {
              if (response.data === "success") {
                this.showFlag = "detail";
                this.showList();
                this.$q.notify({
                  progress: true,
                  message: "게시물 수정 성공",
                  color: "green"
                });
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
        .onCancel(() => {
          return;
        })
        .onDismiss(() => {
          return;
        });
    } else {
      return;
    }
  }
};
</script>
