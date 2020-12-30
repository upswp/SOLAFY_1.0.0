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
export default {
  name: "freereplywrite",
  data() {
    return {
      replyForm: {
        uid: "DFEIJC23WOSKXCNSWQ",
        contents: "",
        articleNo: ""
      }
    };
  },
  //   computed: {
  //     ...mapState(["userInfo"])
  //   },
  props: ["articleNo"],
  methods: {
    onSubmit() {
      //   this.replyForm.userid = this.userInfo.userid;
      if (this.replyForm.contents === "") {
        return;
      }
      this.replyForm.articleNo = this.articleNo;
      Axios.post("freereply/createReply", this.replyForm)
        .then(response => {
          this.replyForm.contents = null;
          this.$emit("freeReplyChanged", response.data);
        })
        .catch(error => console.log(error));
    }
  }
};
</script>

<style></style>
