<template>
  <!-- 댓글 입력 칸 시작 -->
  <div class="q-pa-md" style="max-width: 600px">
    <!-- TODO: 로그인한 사람의 정보에서 가져온다 -->
    <strong>로그인한 사람 닉네임 정보(가져옵니다)</strong>
    <!-- 입력하는 족족 replyForm의 내용에 갱신된다 -->
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
        <!-- TODO: 로그인한 사람의 정보에서 가져온다 -->
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
      replyForm: {
        uid: SessionStorage.getItem("loginUser").uid,
        contents: "",
        // 게시글 번호는
        // 현재 머무르는 게시글의 번
        articleNo: this.$route.params.articleNo
      }
    };
  },
  methods: {
    onSubmit() {
      // 내용이 비어있으면 아무일도 일어나지 않았다
      if (this.replyForm.contents === "") {
        return;
      }

      // 내용이 들어있었다면 댓글 등록 진행
      Axios.post(
        `${this.$store.state.boardType}reply/createReply`,
        this.replyForm
      )
        .then(response => {
          // 댓글이 등록되면 부모컴포넌트에 갱신 요청
          this.replyForm.contents = "";
          this.$emit("replyChanged", response.data);
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
