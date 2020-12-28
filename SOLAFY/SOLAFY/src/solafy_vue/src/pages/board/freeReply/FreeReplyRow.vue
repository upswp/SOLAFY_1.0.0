<template>
  <div style="margin-bottom:40px">
    <div v-if="!modiFlag">
      <strong>{{ reply.contents }}</strong
      ><br />
      <strong>{{ reply.nickname }}</strong>
      작성일자 : {{ reply.regiTime }}

      <q-btn variant="link" @click="modifyThis">수정</q-btn>
      <q-btn variant="link" @click="deleteThis">삭제</q-btn>
    </div>
    <div v-else>
      <textarea
        id="content"
        v-model="reply.contents"
        placeholder="댓글 수정..."
        rows="2"
      ></textarea>

      <q-btn @click="updateReply" variant="dark">댓글수정</q-btn>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
export default {
  name: "freereplyrow",
  props: ["reply"],
  data() {
    return {
      modiFlag: false
    };
  },
  methods: {
    modifyThis: function() {
      this.modiFlag = !this.modiFlag;
    },
    updateReply: function() {
      Axios.post(`freereply/updateReply`, this.reply)
        .then(response => {
          this.modifyThis();
        })
        .catch(error => {
          console.log(error);
        });
    },
    deleteThis: function() {
      console.log(this.reply.replyNo + "글삭제!!");
      if (confirm("정말로 삭제??")) {
        Axios.post(`freereply/deleteReply`, this.reply)
          .then(response => {
            this.$emit("freeReplyChanged", response.data);
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  }
};
</script>

<style></style>
