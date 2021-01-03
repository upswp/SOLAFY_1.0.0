<template>
  <div class="q-pa-md" style="margin: 0 auto; float: center; width: 700px">
    <div class="col" style="margin: 20px">
      <q-btn color="primary" label="돌아가기" @click="goPracticeList" />
    </div>
    <div class="col" style="margin: 20px">
      <q-table
        :title="title"
        :data="info"
        :columns="infoCols"
        row-key="name"
        hide-header
        hide-bottom
        style="max-width :500px;"
      />
      <q-table
        title="문제 목록"
        :data="problems"
        :columns="problemCols"
        style="max-width :500px;"
      />
    </div>
    <div class="col" style="margin: 20px">
      <q-btn
        v-if="perm"
        color="primary"
        label="모의고사 삭제"
        @click="deletePractice"
      />
      <q-btn
        v-if="perm"
        color="primary"
        label="모의고사 수정"
        @click="goUpdatePractice"
      />
      <q-btn color="primary" label="문제풀이 시작" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { notify } from "src/api/common.js";
import { SessionStorage } from "quasar";

export default {
  data() {
    return {
      perm: false,
      title: "",
      info: [],
      problems: [],
      infoCols: [
        { name: "title", align: "left", label: "title", field: "title" },
        {
          name: "content",
          style: "width:100vw",
          label: "content",
          field: "content",
          align: "left"
        }
      ],
      problemCols: [
        {
          name: "문제번호",
          align: "left",
          label: "문제번호",
          field: "problemNo"
        },
        {
          name: "문제제목",
          style: "width:100vw",
          label: "문제 제목",
          field: "title",
          align: "center"
        }
      ]
    };
  },
  mounted() {
    axios
      .get(
        "/practicetest/selectpracticeproblems/" + this.$route.params.practiceNo
      )
      .then(response => {
        if (SessionStorage.getItem("loginUser").uid == response.data.uid)
          this.perm = true;
        this.title = response.data.title;
        this.info = [
          {
            title: "문제 작성자",
            content: response.data.nickname
          },
          {
            title: "문제 작성일자",
            content: response.data.regiTime
          }
        ];
        console.log(this.info);
        this.problems = response.data.problems;
        console.log(this.problems);
      })
      .catch(error => {});
  },
  methods: {
    deletePractice() {
      axios
        .delete("/practicetest/delete/" + this.$route.params.practiceNo)
        .then(response => {
          notify("green", "white", "cloud_done", "모의고사가 삭제 되었습니다");
          this.goPracticeList();
        })
        .catch(error => {
          console.log(error.message);
          notify("red", "white", "warning", "삭제 실패");
        });
    },
    goPracticeList() {
      this.$router.push("/practicelist");
    },
    goUpdatePractice() {
      this.$router.push("/practiceupdate/" + this.$route.params.practiceNo);
    }
  }
};
</script>

<style></style>
