<template>
  <div class="q-pa-md" style="max-width: 600px">
    <strong>로그인 닉네임 정보(sql문 수정필요)</strong>
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
        uid: "DFEIJC23WOSKXCNSWQ",
        contents: "",
        articleNo: ""
      },
      articleNo: this.$route.params.articleNo
    };
  },
  //   computed: {
  //     ...mapState(["userInfo"])
  //   },
  // props: ["articleNo"],
  methods: {
    onSubmit() {
      console.log("hereyouare");
      //   this.replyForm.userid = this.userInfo.userid;
      if (this.replyForm.contents === "") {
        return;
      }
      console.log(this.replyForm.articleNo);
      this.replyForm.articleNo = this.articleNo;
      console.log(this.replyForm.articleNo);
      Axios.post(
        `${this.$store.state.boardType}reply/createReply`,
        this.replyForm
      )
        .then(response => {
          this.replyForm.contents = null;
          this.$emit("replyChanged", response.data);
        })
        .catch(error => console.log(error));
    }
  }
};
</script>

<style></style>
