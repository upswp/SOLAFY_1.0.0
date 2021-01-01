<template>
  <!-- 댓글 목록 불러오기 시작! -->
  <div>
    <div class="q-pa-md" style="max-width: 600px">
      <!-- 수정버튼이 눌리지 않았다면 -->
      <div v-if="!modiFlag">
        <template>
          <strong>{{ reply.nickname }}</strong
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
              <!-- TODO: 댓글올린사람의 nickname으로 프로필 사진을 읽어올수있을까? -->
              <q-avatar>
                <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
              </q-avatar>
            </template>
          </q-input>
          <!-- 작성일자정보 수정/삭제 버튼 시작-->
          <span style="font-size:0.5em">작성일자 : {{ reply.regiTime }}</span>
          <q-btn style="margin-left:60%" @click="modifyThis" dense flat
            >수정</q-btn
          >
          /
          <q-btn @click="deleteThis" dense flat>삭제</q-btn>
          <!-- 작성일자정보 수정/삭제 버튼 끝 -->
        </template>
      </div>

      <!-- 댓글 수정 버튼을 눌렀을때 -->
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
            <!-- TODO: 로그인한 사람 본인 글을 수정할테므로 본인 프사 가져옴 -->
            <q-avatar>
              <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
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
import { mapState } from "vuex";
export default {
  name: "replyrow",
  props: ["reply"],
  data() {
    return {
      // 짧게 쓰기 위해서 boardType을 내부 data에 저장
      boardType: this.$store.state.boardType,
      modiFlag: false
    };
  },
  methods: {
    // 댓글 수정버튼을 눌러서 바뀌는 Flag
    modifyThis: function() {
      this.modiFlag = !this.modiFlag;
    },

    // 댓글 수정
    updateReply: function() {
      Axios.post(`${this.boardType}reply/updateReply`, this.reply)
        .then(response => {
          // 수정이 완료되면 flag를 갱신
          this.modifyThis();
        })
        .catch(error => {
          console.log(error);
        });
    },

    // 댓글 삭제
    deleteThis: function() {
      console.log(this.reply.replyNo + "글삭제!!");
      if (confirm("정말로 삭제??")) {
        Axios.post(`${this.boardType}reply/deleteReply`, this.reply)
          .then(response => {
            // 부모 컴포넌트(BoardDetail)에 댓글을 갱신해달라고 한다
            this.$emit("replyChanged", response.data);
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  }
  // TODO: 미제로 남은 vuex state 사용법
  // computed: {
  //   ...mapState["boardType"]
  // }
};
</script>

<style></style>
