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
          <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
        </q-avatar>
      </template>
      <template v-slot:label> </template>
    </q-input>
    <q-btn @click="onSubmit" dense flat>댓글등록</q-btn>
  </div>
</template>

<script>
// import { mapState } from "vuex";
import Axios from "axios";
import { mapState } from "vuex";
export default {
  name: "replywrite",
  data() {
    return {
      replyForm: {
        // 로그인한 사람의 uid를 가져온다.
        uid: "DFEIJC23WOSKXCNSWQ",
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
      Axios.post(`${this.$store.state.boardType}reply/createReply`)
        .then(response => {
          // 댓글이 등록되면 부모컴포넌트에 갱신 요청
          this.$emit("replyChanged", response.data);
        })
        .catch(error => console.log(error));
    }
  }
};
</script>

<style></style>
