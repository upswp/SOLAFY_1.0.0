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
          <q-item-label overline> # {{ article.articleNo }} </q-item-label>
          <q-item-label
            ><q-chip
              dense
              color="orange"
              text-color="white"
              v-if="article.notice"
            >
              공지 </q-chip
            >{{ article.title }}</q-item-label
          >
          <q-item-label caption>
            닉네임 : {{ article.nickname }} <br />
            작성시간 : {{ article.regiTime }}
          </q-item-label>
        </q-item-section>
      </q-item>
      <q-separator />
      <q-card-section>
        <q-card-section class="col-4">{{ article.contents }} </q-card-section>
      </q-card-section>
      <template v-if="this.$store.state.boardType === `free`">
        <q-card-section align="right">
          <q-rating
            id="likeArea"
            v-model="likeBtn"
            max="1"
            size="1em"
            color="red"
            color-selected="red-9"
            icon="favorite_border"
            icon-selected="favorite"
            icon-half="favorite"
            no-dimming
          />
          <label for="#likeArea">
            LikeCount :
            {{ article.likeCount + likeBtn }}
          </label>
        </q-card-section>
      </template>
      <q-separator />
      <q-card-actions align="right">
        <q-btn color="primary" label="글 수정" @click="goToUpdate" />
        <q-btn color="red" label="글 삭제" @click="deleteArticle" />
        <q-btn color="green" label="글 목록보기" @click="goToBoard" />
      </q-card-actions>
      <q-separator />
      <q-card-section align="left">
        <reply-write
          :articleNo="this.articleNo"
          @replyChanged="showChangedReply()"
        ></reply-write>
      </q-card-section>
      <q-separator />
      <q-card-section>
        <reply-row
          v-for="(reply, index) in replies"
          :reply="reply"
          :key="index"
          @replyChanged="showChangedReply()"
        ></reply-row>
      </q-card-section>
    </q-card>
  </q-page>
</template>
<script>
// 자유게시판 댓글 컴포넌트 가져오기
import ReplyWrite from "components/board/reply/ReplyWrite.vue";
import ReplyRow from "components/board/reply/ReplyRow.vue";
import Axios from "axios";
import { mapState } from "vuex";
export default {
  data() {
    return {
      likeBtn: 0,
      article: [],
      replies: [],
      boardType: this.$store.state.boardType,
      type: "",
      articleNo: this.$route.params.articleNo
    };
  },
  components: {
    ReplyWrite,
    ReplyRow
  },
  methods: {
    deleteArticle: function() {
      this.$q
        .dialog({
          title: "Confirm",
          message: "정말 삭제하시겠습니까?",
          cancel: true,
          persistent: true
        })
        .onOk(() => {
          Axios.delete(`/${this.boardType}/deleteArticle/${this.articleNo}`)
            .then(response => {
              if (response.data == "success") {
                this.showFlag = "list";
                this.goToBoard();
                this.resetForm();
                this.$q.notify({
                  progress: true,
                  timeout: 3,
                  message: "삭제 완료!",
                  color: "green"
                });
              } else {
                this.$q.notify({
                  progress: true,
                  timeout: 3,
                  message: "삭제 실패!",
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
    }, // 댓글 목록
    getReplyRow: function() {
      console.log(this.articleNo);
      Axios.get(`${this.boardType}reply/selectReplies/${this.articleNo}`)
        .then(response => {
          this.replies = response.data;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    showChangedReply: function() {
      // this.replies = reply;
      console.log("현재 게시물 번호", this.articleNo);
      this.getReplyRow(this.articleNo);
    },
    goToBoard: function() {
      this.$router.push({
        name: `${this.boardType}-board-list`
      });
    },
    goToUpdate: function() {
      this.$router.push({
        name: `${this.boardType}-board-update`,
        params: { articleNo: this.articleNo }
      });
    }
  },
  mounted() {
    console.log(this.articleNo);
    Axios.get(`/${this.boardType}/selectArticleByArticleNo/${this.articleNo}`)
      .then(response => {
        this.article = response.data;
        this.getReplyRow(this.articleNo);
      })
      .catch(() => {
        this.errored = true;
      })

      .finally(() => (this.loading = false));
  },
  // computed: {
  //   ...mapState["boardType"]
  // },
  create() {}
};
</script>
