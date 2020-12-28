<template>
  <div>
    <textarea
      id="content"
      v-model="replyForm.contents"
      placeholder="댓글 삽입..."
      rows="2"
    ></textarea>

    <q-btn @click="onSubmit" variant="dark">댓글등록</q-btn>
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
