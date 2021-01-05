<template>
  <!-- 댓글 입력 칸 시작 -->
  <div class="q-pa-md" style="max-width: 600px">
    <!-- 로그인한 사람의 닉네임 표시 -->
    작성자 : <b>{{ nickname }}</b>
    <q-input
      type="textarea"
      v-model="replyForm.contents"
      placeholder="댓글을 입력해주세요"
      label-slot
      autogrow
      clearable
      outlined
    >
      <template v-slot:prepend>
        <!-- 로그인한 회원의 프로필 사진 표시 -->
        <q-avatar>
          <img :src="profileImageUrl" />
        </q-avatar>
      </template>
      <template v-slot:label> </template>
    </q-input>
    <q-btn @click="onSubmit" dense flat>댓글등록</q-btn>
  </div>
</template>

<script>
import Axios from "axios";
import { firebaseAuth, firebaseSt } from "boot/firebase";
import { SessionStorage, uid } from "quasar";
import { mapState } from "vuex";
export default {
  name: "replywrite",
  data() {
    return {
      // 프로필 이미지 주소를 저장
      profileImageUrl: "",
      // 로그인한 회원의 닉네임 저장
      nickname: SessionStorage.getItem("loginUser").nickname,
      // 입력한 댓글 정보가 들어갈 배열
      replyForm: {
        // 작성자는 로그인한 회원 본인
        uid: SessionStorage.getItem("loginUser").uid,
        // 내용이 바인딩 될 contents
        contents: "",
        // 게시글 번호는 현재 머무르는 게시글의 번호
        articleNo: this.$route.params.articleNo
      }
    };
  },
  methods: {
    /**
     * @Method설명 : 댓글 등록 요청 메소드
     * @변경이력 :
     */
    onSubmit() {
      // 내용이 비어 있으면 댓글이 등록되지 않도록 한다
      if (this.replyForm.contents === "") {
        return;
      }

      // 내용이 들어 있으면 댓글 등록 진행
      Axios.post(
        `${this.$store.state.boardType}reply/createReply`,
        this.replyForm
      )
        .then(response => {
          // 댓글이 등록되면 부모컴포넌트에 댓글 정보 갱신 요청
          this.replyForm.contents = "";
          // TODO : 댓글을 작성한 후에 프로필 사진이 바로 갱신 되지 않는 버그(새로고침으로 일단 기능 완료)
          // this.$router.go({
          //   name: `${this.boardType}-board-detail`,
          //   params: { articleNo: this.articleNo }
          // });
          this.$router.go(0);
          // 프로필사진 관련 이슈가 없을 경우,
          // emit으로 이벤트를 발생시켜 페이지를 갱신할 필요 없음
          // this.$emit("replyChanged", response.data);
        })
        .catch(error => console.log(error));
    },

    /**
     * @Method설명 : uid에 해당하는 사용자의 프로필 이미지를 가져오는 메소드
     * @변경이력 :
     */
    getProfileImageUrl: function() {
      firebaseSt
        .ref()
        .child("profileimg/" + SessionStorage.getItem("loginUser").uid)
        .getDownloadURL()
        .then(url => {
          this.profileImageUrl = url;
        })
        .catch(error => {
          // 회원의 프로필 사진을 불러오지 못했을 경우 기본 이미지로 대체
          this.profileImageUrl = "https://cdn.quasar.dev/img/boy-avatar.png";
          console.log("error is ", error);
        });
    }
  },
  created() {
    this.getProfileImageUrl();
  }
};
</script>

<style></style>
