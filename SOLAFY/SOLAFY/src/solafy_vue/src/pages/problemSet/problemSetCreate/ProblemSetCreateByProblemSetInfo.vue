<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
      <div class="row">
        <!-- 문제집 정보 입력 -->
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
            @click="goToProblemSet"
          />
        </div>
        <div class="col">
          <q-btn
            color="primary"
            label="문제 제출 시작"
            @click="goToProblemSetCreateByProblem"
            style="float:right"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Axios from "axios";
import { firebaseAuth } from "src/boot/firebase";
import { SessionStorage } from "quasar";
import { notify } from "src/api/common.js";

export default {
  name: "ProblemSetCreateByProblemSetInfo",
  data() {
    return {
      // 작성자 닉네임
      nickname: SessionStorage.getItem("loginUser").nickname,
      // 문제집 정보
      ProblemSet: {
        // 작성자 uid
        uid: firebaseAuth.currentUser.uid,
        // 문제집 제목
        title: ""
      }
    };
  },
  methods: {
    /**
     * @Method설명 : 문제집 리스트로 이동
     * @변경이력 :
     */
    goToProblemSet() {
      this.$router.push({ name: "ProblemSet" });
    },
    /**
     * @Method설명 : 문제집을 db에 임시 저장 후 출제 화면으로 이동
     * @변경이력 :
     */
    goToProblemSetCreateByProblem() {
      // 예외처리
      if (this.ProblemSet.title == "") {
        notify("warning", "white", "warning", "문제집 제목을 입력해주세요");
        return;
      }
      // 문제집 임시 등록
      Axios
        .post("problem/problemset/createProblemSet", this.ProblemSet)
        .then(response => {
          this.$router.push({
            name: "ProblemSetCreateByProblem",
            params: {
              problemSetNo: response.data
            }
          });
        })
        .catch(error => {
          notify("red", "white", "error", "문제집 임시저장 실패");
        });
    }
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
