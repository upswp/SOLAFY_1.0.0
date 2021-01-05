<template>
  <!-- 게시글 상세페이지 시작 -->
  <q-page class="q-pa-md">
    <q-card flat bordered>
      <q-item>
        <q-item-section avatar>
          <!-- 게시글 작성자의 프로필 사진 표시 -->
          <q-avatar>
            <img :src="profileImageUrl" />
          </q-avatar>
        </q-item-section>

        <!-- 게시글 기본 정보(게시글 번호 제목, 닉네임, 작성시간) 출력-->
        <q-item-section>
          <q-item-label overline> # {{ article.articleNo }} </q-item-label>
          <!--공지글인 경우에는 공지마크 출력 -->
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

      <!-- 문제 정보 표시 컴포넌트 시작(답안수정게시판, 질문게시판 한정) -->
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
        <template v-if="isQualified">
          <q-btn color="primary" label="글 수정" @click="goToUpdate" />
          <q-btn color="red" label="글 삭제" @click="deleteArticle" />
        </template>
        <q-btn color="green" label="목록으로" @click="goToBoard" />
      </q-card-actions>
      <!-- 수정/삭제/목록으로 돌아가기 버튼 영역 끝-->

      <q-separator />
      <q-card-section align="left">
        <!-- 댓글 입력칸 컴포넌트 시작 -->
        <reply-write
          :articleNo="this.articleNo"
          @replyChanged="getReplyRow()"
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
          @replyChanged="getReplyRow()"
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
import { firebaseAuth, firebaseSt } from "boot/firebase";
import { SessionStorage, uid } from "quasar";
export default {
  data() {
    return {
      // 로그인 유저의 게시글 수정/삭제 권한 여부 flag
      isQualified: false,
      // 프로필 이미지 주소를 저장
      profileImageUrl: "",
      // 좋아요 버튼 기본값
      likeBtn: 0,
      // 게시글 상세 내용 저장 배열
      article: [],
      // 해당 게시글의 댓글 저장 배열
      replies: [],
      // store에 저장되어있는 현재 이용하고 있는 게시판 타입
      boardType: this.$store.state.boardType,
      // url에서 게시글 번호를 받아와 저장
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
    /**
     * @Method설명 : 게시글 번호에 해당하는 게시물 요청 메서드
     * @변경이력 :
     */
    selectArticleByArticleNo: function() {
      Axios.get(`/${this.boardType}/selectArticleByArticleNo/${this.articleNo}`)
        .then(response => {
          // 반환된 게시글 정보 저장
          this.article = response.data;
          // 해당 게시물의 댓글 정보 읽어옴
          this.getReplyRow(this.articleNo);
          this.getProfileImageUrl(this.article.uid);
          // 해당 게시물의 uid를 이용하여 권한 여부 판단
          this.setIsQualified();
        })
        .catch(() => {
          this.errored = true;
        })

        .finally(() => (this.loading = false));
    },
    /**
     * @Method설명 : 게시글 삭제 요청 메서드
     * @변경이력 :
     */
    deleteArticle: function() {
      // 삭제 재확인
      this.$q
        .dialog({
          title: "Confirm",
          message: "정말 삭제하시겠습니까?",
          cancel: true,
          persistent: true
        })
        // 대화상자에서 OK버튼을 누르면 삭제 진행
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

    /**
     * @Method설명 : 해당 게시글의 댓글 배열을 불러오는 메소드
     * @변경이력 :
     */
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
     * @Method설명 : 현재 로그인 사용자가 댓글 수정/삭제 권한이 있는지 판단
     * @변경이력 :
     */
    setIsQualified: function() {
      var loginUserUid = SessionStorage.getItem("loginUser").uid;
      this.isQualified = loginUserUid === this.article.uid;
      Axios.get(`user/selectbyuid/${loginUserUid}`)
        .then(response => {
          if (response.data.admin === 0) this.isQualified = true;
        })
        .catch(error => console.log(error))
        .finally(() => {
          this.loading = true;
        });
    }
  },

  created() {
    this.selectArticleByArticleNo();
  }
};
</script>
