<template>
  <q-card-section align="left">
    <q-separator />
    문제 제출자 : <strong>{{ problemInfo.nickname }}</strong
    ><br />
    {{ problemNo }}번 문제 : <strong>{{ problemInfo.contents }}</strong>

    <template v-if="problemInfo.type === 0">
      <div v-for="(choice, index) in multipleChoice" :key="index">
        {{ index + 1 }} : {{ choice }}
      </div>
    </template>
    <q-separator />
    <template>
      기존 답 :
      <strong>{{ answerInfo.answer }}</strong>
      <q-separator />

      <strong> {{ answerInfo.keyword }}</strong>
      기존 해설 : <strong>{{ answerInfo.solution }}</strong>
    </template>
    <q-separator />
  </q-card-section>
</template>
<script>
import Axios from "axios";

export default {
  name: "boardprobleminfo",
  props: { problemNo: String },
  data() {
    return {
      problemInfo: [],
      answerInfo: [],
      error: null,
      loading: null,
      multipleChoice: [],
      problemGetNo: ""
    };
  },
  method: {},
  watch: {
    problemNo: function() {
      // 문제 정보를 불러온다
      console.log("ㅎㅎ" + this.problemNo);
      this.problemGetNo = this.problemNo;
      Axios.get(`problem/` + this.problemGetNo)
        .then(response => {
          // 문제 정보 저장
          this.problemInfo = response.data["problem"];
          this.multipleChoice = this.problemInfo.multipleChoice.split(",");

          // 문제 정보를 불러온다
          Axios.get(`problem/answer/` + this.problemGetNo)
            .then(response => {
              // 문제 정보 저장
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
