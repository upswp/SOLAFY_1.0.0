<template>
  <!-- 게시글 상세페이지 시작 -->
  <q-page class="q-pa-md">
    <q-card flat bordered>
      <q-item>
        <q-item-section avatar>
          <!-- TODO: 작성자의 프로필 사진을 가져와야하는데 서버에 요청해야하나 -->
          <q-avatar>
            <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
          </q-avatar>
        </q-item-section>

        <!-- 게시글 기본 정보(게시글 번호 제목, 닉네임, 작성시간) 출력
            공지글인 경우에는 공지마크도 붙여준다 -->
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
      <!-- 게시글 기본 정보 출력 끝 -->

      <!-- 문제 정보 표시 시작(답안수정게시판, 질문게시판 한정) -->
      <board-problem-info
        :problemNo="article.problemNo"
        v-if="
          this.boardType === `answermodify` || this.boardType === `question`
        "
      />
      <!-- 문제 정보 표시 끝 -->

      <!-- 내용 표시 시작 -->
      <q-card-section>
        <q-card-section class="col-4">{{ article.contents }} </q-card-section>
      </q-card-section>
      <!-- 내용 표시 끝 -->

      <!-- 좋아요 정보 표시 시작 -->
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
            <!-- TODO:  DB와의 작업을 통해서 실제로 동작하도록 설정 -->
            {{ article.likeCount + likeBtn }}
          </label>
        </q-card-section>
      </template>
      <q-separator />
      <!-- 좋아요 정보 표시 끝 -->

      <!-- 수정/삭제/목록으로 돌아가기 버튼 영역 시작-->
      <q-card-actions align="right">
        <q-btn color="primary" label="글 수정" @click="goToUpdate" />
        <q-btn color="red" label="글 삭제" @click="deleteArticle" />
        <q-btn color="green" label="목록으로" @click="goToBoard" />
      </q-card-actions>
      <!-- 수정/삭제/목록으로 돌아가기 버튼 영역 끝-->

      <q-separator />
      <q-card-section align="left">
        <!-- 댓글 입력칸 컴포넌트 시작 -->
        <reply-write
          :articleNo="this.articleNo"
          @replyChanged="showChangedReply()"
        ></reply-write>
        <!-- 댓글 입력칸 컴포넌트 끝 -->
      </q-card-section>
      <q-separator />
      <q-card-section>
        <!-- 댓글 표시칸 컴포넌트 시작 -->
        <reply-row
          v-for="(reply, index) in replies"
          :reply="reply"
          :key="index"
          @replyChanged="showChangedReply()"
        ></reply-row>
        <!-- 댓글 표시칸 컴포넌트 끝 -->
      </q-card-section>
    </q-card>
  </q-page>
</template>
<script>
// 자유게시판 댓글 컴포넌트 가져오기
import ReplyWrite from "components/board/reply/ReplyWrite.vue";
import ReplyRow from "components/board/reply/ReplyRow.vue";
// 문제 정보 표시 컴포넌트 가져오기
import BoardProblemInfo from "components/board/materials/BoardProblemInfo.vue";

import Axios from "axios";
import { mapState } from "vuex";
import { SessionStorage } from "quasar";
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
  // 컴포넌트 선언
  components: {
    BoardProblemInfo,
    ReplyWrite,
    ReplyRow
  },
  methods: {
    // 게시글 삭제 메서드
    deleteArticle: function() {
      // 삭제 재확인
      this.$q
        .dialog({
          title: "Confirm",
          message: "정말 삭제하시겠습니까?",
          cancel: true,
          persistent: true
        })
        // 대화상자에서 확인버튼을 누르면 삭제 진행
        .onOk(() => {
          Axios.delete(`/${this.boardType}/deleteArticle/${this.articleNo}`)
            .then(response => {
              if (response.data == "success") {
                // 게시글 목록으로 돌아가기
                this.goToBoard();
                this.$q.notify({
                  progress: true,
                  timeout: 3,
                  message: "삭제 완료!",
                  color: "green"
                });
                // 게시글 등록에 실패하였을 경우
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
        // 대화상자에서 취소버튼을 누르거나 무시한다면 게시글 상세페이지에 머무름
        .onCancel(() => {
          return;
        })
        .onDismiss(() => {
          return;
        });
    },

    // 댓글 목록
    getReplyRow: function() {
      // vuex에 저장된 게시판 형식(boardType)을 이용하여,
      // 해당 게시판의 해당 게시글의 댓글들을 불러온다
      Axios.get(`${this.boardType}reply/selectReplies/${this.articleNo}`)
        .then(response => {
          // 가져온 댓글들을 저장
          this.replies = response.data;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },

    // 댓글이 갱신되었다면 댓글을 다시 가져오도록 요청
    showChangedReply: function() {
      console.log("현재 게시물 번호", this.articleNo);
      this.getReplyRow(this.articleNo);
    },

    // 해당 게시판의 목록으로 돌아간다
    goToBoard: function() {
      this.$router.push({
        name: `${this.boardType}-board-list`
      });
    },

    // 해당 게시글의 수정페이지로 넘어간다
    goToUpdate: function() {
      this.$router.push({
        name: `${this.boardType}-board-update`,
        params: { articleNo: this.articleNo }
      });
    }
  },

  // 시작할때 해당 글 정보를 읽어들여와서 저장한다.
  mounted() {
    Axios.get(`/${this.boardType}/selectArticleByArticleNo/${this.articleNo}`)
      .then(response => {
        // 반환된 게시글 정보 저장
        this.article = response.data;
        // 해당 게시물의 댓글 정보 읽어옴
        this.getReplyRow(this.articleNo);
      })
      .catch(() => {
        this.errored = true;
      })

      .finally(() => (this.loading = false));
  }

  /**
   * TODO: 어디서는 이렇게해서 바로 자신의 DATA처럼 사용하는데, 여기선 this.$store.state.~~ 의 형식으로밖에 불러올수없다.
   * TODO: (이어서) computed의 특성상 한번 변경되고나서 다시 불러오지 못해서 그럴수도 있다
   *  */
  // computed: {
  //   ...mapState["boardType"]
  // },
};
</script>
