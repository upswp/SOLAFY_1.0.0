<template>
  <!-- 댓글 목록 불러오는 부분 시작 -->
  <div>
    <div class="q-pa-md" style="max-width: 600px">
      <!-- (댓글) 수정 버튼을 누르지 않았을 때 -->
      <div v-if="!isModify">
        <template>
          <b>{{ reply.nickname }}</b
          ><br />
          <!-- readonly로 댓글들을 보여준다 -->
          <q-input
            type="textarea"
            v-model="reply.contents"
            label-slot
            readonly
            autogrow
          >
            <template v-slot:prepend>
              <!-- 댓글을 작성한 회원의 프로필 사진 표시 -->
              <q-avatar>
                <img :src="profileImageUrl" />
              </q-avatar>
            </template>
          </q-input>
          <!-- 작성일자정보 수정/삭제 버튼 시작-->
          <span style="font-size:0.5em">작성일자 : {{ reply.regiTime }}</span>
          <template v-if="isQualified">
            <q-btn style="margin-left:60%" @click="isModify = true" dense flat
              >수정</q-btn
            >
            /
            <q-btn @click="deleteReply" dense flat>삭제</q-btn>
          </template>
          <!-- 작성일자정보 수정/삭제 버튼 끝 -->
        </template>
      </div>

      <!-- 댓글 수정 버튼을 눌렀을때 -->
      <div v-else>
        <b>{{ reply.nickname }}</b>
        <q-input
          type="textarea"
          v-model="reply.contents"
          label-slot
          autogrow
          outlied
          filled
        >
          <template v-slot:prepend>
            <!-- 댓글을 수정할 본인의 프로필 사진 표시 -->
            <q-avatar>
              <img :src="profileImageUrl" />
            </q-avatar>
          </template>
          <template v-slot:label> </template>
        </q-input>
        <span style="font-size:0.5em">작성일자 : {{ reply.regiTime }}</span>
        <q-btn style="margin-left:69%" @click="updateReply" dense flat
          >확인</q-btn
        >
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import { firebaseAuth, firebaseSt } from "boot/firebase";
import { SessionStorage, uid } from "quasar";
import { mapState } from "vuex";
export default {
  name: "replyrow",
  // 부모 컴포넌트로부터 props 받은 댓글 1개의 정보
  props: ["reply"],
  data() {
    return {
      // 로그인 사용자의 수정/삭제 권한 여부 flag
      isQualified: false,
      // 게시판 형식 값을 저장
      boardType: this.$store.state.boardType,
      // (댓글) 수정 버튼이 눌렸는지 여부 확인용 flag
      isModify: false,
      // 프로필 사진 주소를 저장
      profileImageUrl: ""
    };
  },
  methods: {
    /**
     * @Method설명 : 댓글 수정 요청 메서드
     * @변경이력 :
     */
    updateReply: function() {
      Axios.post(`${this.boardType}reply/updateReply`, this.reply)
        .then(response => {
          // 수정이 완료되면 flag를 갱신
          this.isModify = false;
          console.log(this.reply);
          // 부모 컴포넌트(BoardDetail)에 댓글을 갱신 요청
          // this.getProfileImageUrl();
          this.$emit("replyChanged", response.data);
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * @Method설명 : 댓글 삭제 요청 메서드
     * @변경이력 :
     */
    deleteReply: function() {
      console.log(this.reply.replyNo + "글삭제!!");
      if (confirm("정말로 삭제??")) {
        Axios.post(`${this.boardType}reply/deleteReply`, this.reply)
          .then(response => {
            // 부모 컴포넌트(BoardDetail)에 댓글을 갱신 요청
            this.getProfileImageUrl();
            // TODO : 댓글을 작성한 후에 프로필 사진이 바로 갱신 되지 않는 버그(새로고침으로 일단 기능 완료)
            this.$router.go({
              name: `${this.boardType}-board-detail`,
              params: { articleNo: this.articleNo }
            });
            // this.$emit("replyChanged", response.data);
          })
          .catch(error => {
            console.log(error, "is error");
          });
      }
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
          // 회원의 프로필 사진을 불러오지 못했을 경우 기본 이미지로 대체

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
      this.isQualified = loginUserUid === this.reply.uid;
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
  mounted() {
    this.getProfileImageUrl(this.reply.uid);
    this.setIsQualified();
  }
};
</script>

<style></style>
