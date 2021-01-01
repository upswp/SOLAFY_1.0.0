<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
      <div class="row">
        <q-markup-table separator="cell">
          <tbody>
            <tr>
              <td class="header">문제집 제목</td>
              <td class="content">
                <q-input v-model="ProblemSet.title" borderless />
              </td>
            </tr>
            <tr>
              <td class="header">문제집 작성자</td>
              <td class="content">{{ nickname }}</td>
            </tr>
          </tbody>
        </q-markup-table>
      </div>
      <!-- 버튼들 -->
      <div class="row q-gutter-md button">
        <div class="col">
          <q-btn
            color="primary"
            label="목록으로 돌아가기"
            outline
            @click="goToproblemSetList"
          />
        </div>
        <div class="col">
          <q-btn
            color="primary"
            label="문제 제출 시작"
            @click="goToCreateProblemList"
            style="float:right"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { firebaseAuth } from "src/boot/firebase";
import { notify } from "src/api/common.js";

export default {
  name: "ProblemSetCreateByProblemSetInfo",
  data() {
    return {
      nickname: "",
      ProblemSet: {
        uid: "",
        title: ""
      }
    };
  },
  methods: {
    // User의 Nickname반환
    selectNickname() {
      axios
        .get("user/selectbyuid/" + firebaseAuth.currentUser.uid)
        .then(response => {
          this.nickname = response.data.nickname;
        })
        .catch(error => {
          notify("red", "white", "error", "닉네임 불러오기 실패");
          this.$router.go(-1);
        });
    },
    // 문제집 리스트로 이동
    goToproblemSetList() {
      this.$router.push({ name: "ProblemSet" });
    },
    // 문제집을 db에 임시 저장 후 출제 화면으로 이동
    goToCreateProblemList() {
      this.ProblemSet.uid = firebaseAuth.currentUser.uid;
      axios
        .post("problem/problemset/createProblemSet", this.ProblemSet)
        .then(response => {
          if (response.data == "success") {
            this.$router.push({ name: "ProblemSetCreateByProblem" });
          } else {
            notify("red", "white", "error", "문제집 임시저장 실패");
          }
        })
        .catch(error => {
          notify("red", "white", "error", "문제집 임시저장 실패");
        });
    }
  },
  created() {
    this.selectNickname();
  }
};
</script>
<style scoped>
.header {
  text-align: center;
  width: 100px;
}
.content {
  width: 100vw;
}
.button {
  margin-top: 10px;
}
</style>
