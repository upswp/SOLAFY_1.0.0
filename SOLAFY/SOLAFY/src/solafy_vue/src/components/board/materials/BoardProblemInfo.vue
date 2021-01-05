<template>
  <!-- 문제 정보 표시 시작-->
  <q-card-section align="left">
    <q-separator />
    {{ problemInfo.problemNo }}번 문제<br />
    <span style="font-size:0.5em">문제 작성자 : {{ problemInfo.nickname }}</span
    ><br />
    <b>{{ problemInfo.contents }}</b
    ><br />
    <!-- 객관식 문제일 경우, 기존의 답과 해설 표시 시작-->
    <template v-if="problemInfo.type === 0">
      <div v-for="(choice, index) in multipleChoice" :key="index">
        {{ index + 1 }} : {{ choice }}
      </div>
      <q-separator />
      <template>
        기존 답 :
        <b>{{ answerInfo.answer }}</b>
        <q-separator />

        <b> {{ answerInfo.keyword }}</b>
        기존 해설 : <b>{{ answerInfo.solution }}</b>
      </template>
    </template>
    <template v-else>
      <div>
        <span style="font-size:0.3em"
          ><i>*주관식과 서술형은 정답이 제공되지 않습니다.*</i></span
        >
      </div>
    </template>
    <!-- 객관식 문제, 답과 해설 끝 -->
    <q-separator />
  </q-card-section>
  <!-- 문제정보 출력 끝 -->
</template>
<script>
import Axios from "axios";

export default {
  name: "boardprobleminfo",
  props: ["problemNo"],
  data() {
    return {
      error: null,
      loading: null,

      // 다루고자 하는 문제 정보 저장
      problemInfo: [],
      // 객관식 문제의 답 정보 저장
      answerInfo: [],
      // 객관식 문제의 선택지 정보 저장
      multipleChoice: []
    };
  },
  method: {},
  watch: {
    problemNo: function() {
      // 문제 정보를 불러온다
      Axios.get(`problem/` + this.problemNo)
        .then(response => {
          // 문제 정보 저장
          this.problemInfo = response.data["problem"];
          this.multipleChoice = this.problemInfo.multipleChoice.split(",");

          // 답안 정보를 불러온다
          Axios.get(`problem/answer/` + this.problemNo)
            .then(response => {
              // 답안 정보 저장
              this.answerInfo = response.data;
              console.log(this.answerInfo);
            })
            .catch(() => (this.error = true))
            .finally(() => (this.loading = false));
        })
        .catch(() => (this.error = true))
        .finally(() => (this.loading = false));
    }
  }
};
</script>
