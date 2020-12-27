<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="fixed-center col" v-if="loading">
      <q-spinner-dots color="primary" size="6em" />
    </div>
    <div class="col-md-6" v-else>
      <div class="row">
        <q-table
          :data="data"
          :columns="columns"
          hide-header
          hide-bottom
          separator="cell"
        />
      </div>
      <div class="content">
        <div>
          <q-markdown>{{ item.problem.contents }}</q-markdown>
        </div>
        <div class="answer" v-if="item.problem.type === 0">
          <div
            v-for="(choice, index) in multipleChoice"
            :key="index"
          >
            <q-checkbox :val="index + 1" v-model="checklist">
              <q-markdown>{{ index + 1 }}. {{ choice }}</q-markdown>
            </q-checkbox>
          </div>
        </div>
        <div class="answer" v-else-if="item.problem.type === 1">
          <q-input v-model="answer" outlined dense />
        </div>
        <div class="answer" v-else-if="item.problem.type === 2">
          <q-input v-model="answer" dense outlined autogrow />
        </div>
        <div class="q-gutter-md button">
        <q-btn color="primary" label="목록으로 돌아가기" outline @click="goToproblemList"/>
        <q-btn color="primary" label="문제답안제출" @click="goToResult"/>
        <q-btn color="amber" label="문제수정요청" @click="goToAnswerModify"/>
        </div>
        <hr />
        <div class="row">
            <p v-for="(ht, index) in item.hashTag" :key="index">
              #{{ ht.hashTag }}
            </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ProblemDetail",
  data() {
    return {
      columns: [
        { name: "title", align: "left", label: "title", field: "title" },
        {
          name: "content",
          align: "left",
          label: "content",
          field: "content",
          style: "width:100vw"
        }
      ],
      data: [
        {
          title: "문제 제목",
          content: ""
        },
        {
          title: "문제 카테고리",
          content: ""
        },
        {
          title: "문제 작성자",
          content: ""
        },
        {
          title: "작성일자",
          content: ""
        }
      ],
      multipleChoice: [],
      item: {
        problem: {
          problemNo: 0,
          multipleChoice: "",
          title: "",
          contents: "",
          categoryNo: "",
          type: 0,
          regiTime: "",
          nickname: ""
        },
        categoryLarge: {
          categoryNo: 0,
          categoryName: ""
        },
        categoryMedium: {
          categoryNo: 0,
          categoryName: ""
        },
        categorySmall: {
          categoryNo: 0,
          categoryName: ""
        },
        hashTag: []
      },
      checklist: [],
      answer: "",
      loading: true
    };
  },
  created() {
    axios
      .get("problem/" + this.$route.params.problemNo)
      .then(Response => {
        this.item = Response.data;
        if (this.item.problem.multipleChoice != null) {
          this.multipleChoice = this.item.problem.multipleChoice.split(",");
        }
        this.data[0].content = this.item.problem.title;
        this.data[1].content =
          this.item.categoryLarge.categoryName +
          ">" +
          this.item.categoryMedium.categoryName +
          ">" +
          this.item.categorySmall.categoryName;
        this.data[2].content = this.item.problem.nickname;
        this.data[3].content = this.item.problem.regiTime;
        this.loading = false;
      })
      .catch(error => {
        alert(error);
      });
  },
  methods : {
    goToproblemList(){
      this.$router.push("/problem");
    },
    goToResult(){

    },
    goToAnswerModify(){

    }
  }
};
</script>

<style scoped>
.content {
  margin-left: 5%;
  margin-top: 2%;
}
.answer {
  margin-bottom:20px;
}
.button{
  text-align: center;
}
</style>
