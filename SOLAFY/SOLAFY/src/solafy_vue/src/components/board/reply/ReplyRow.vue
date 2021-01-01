<template>
  <div>
    <div class="q-pa-md" style="max-width: 600px">
      <div v-if="!modiFlag">
        <template>
          <strong>{{ reply.nickname }}</strong
          ><br />
          <q-input
            type="textarea"
            v-model="reply.contents"
            label-slot
            readonly
            autogrow
          >
            <template v-slot:prepend>
              <q-avatar>
                <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
              </q-avatar>
            </template>
            <template v-slot:label> </template>
          </q-input>
          <span style="font-size:0.5em">작성일자 : {{ reply.regiTime }}</span>
          <q-btn style="margin-left:60%" @click="modifyThis" dense flat
            >수정</q-btn
          >
          /
          <q-btn @click="deleteThis" dense flat>삭제</q-btn>
        </template>
      </div>
      <div v-else>
        <strong>{{ reply.nickname }}</strong>
        <q-input
          type="textarea"
          v-model="reply.contents"
          label-slot
          autogrow
          outlied
          filled
        >
          <template v-slot:prepend>
            <q-avatar>
              <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
            </q-avatar>
          </template>
          <template v-slot:label> </template>
        </q-input>
        <span></span>
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
import { mapState } from "vuex";
export default {
  name: "replyrow",
  props: ["reply"],
  data() {
    return {
      boardType: this.$store.state.boardType,
      modiFlag: false
    };
  },
  methods: {
    modifyThis: function() {
      this.modiFlag = !this.modiFlag;
    },
    updateReply: function() {
      Axios.post(`${this.boardType}reply/updateReply`, this.reply)
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
        Axios.post(`${this.boardType}reply/deleteReply`, this.reply)
          .then(response => {
            this.$emit("replyChanged", response.data);
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  },
  computed: {
    ...mapState["boardType"]
  }
};
</script>

<style></style>
